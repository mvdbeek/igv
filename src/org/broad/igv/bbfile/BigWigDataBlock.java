/*
 * Copyright (c) 2007-2012 The Broad Institute, Inc.
 * SOFTWARE COPYRIGHT NOTICE
 * This software and its documentation are the copyright of the Broad Institute, Inc. All rights are reserved.
 *
 * This software is supplied without any warranty or guaranteed support whatsoever. The Broad Institute is not responsible for its use, misuse, or functionality.
 *
 * This software is licensed under the terms of the GNU Lesser General Public License (LGPL),
 * Version 2.1 which is available at http://www.opensource.org/licenses/lgpl-2.1.php.
 */

package org.broad.igv.bbfile;

import net.sf.samtools.seekablestream.SeekableStream;
import org.apache.log4j.Logger;
import org.broad.igv.util.CompressionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: martind
 * Date: Apr 16, 2010
 * Time: 10:32:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class BigWigDataBlock {

    private static Logger log = Logger.getLogger(BigWigDataBlock.class);

    // BigWig data types sizes
    final int FIXED_STEP_ITEM_SIZE = 4;
    final int VAR_STEP_ITEM_SIZE = 8;
    final int BED_GRAPH_ITEM_SIZE = 12;

    // Bed data block access variables   - for reading in bed records from a file
    private long fileOffset;       // Wig data block file offset
    private long leafDataSize;     // byte size for data block specified in the R+ leaf
    private boolean isLowToHigh;   // if true, data is low to high byte order; else high to low

    // defines the bigWig data source
    private HashMap<Integer, String> chromosomeMap;  // map of chromosome ID's and corresponding names
    private RPTreeLeafNodeItem leafHitItem;   // R+ leaf item containing data block location

    // uncompressed byte stream buffer and readers
    private byte[] wigBuffer;      // buffer containing leaf block data uncompressed
    private int remDataSize;       // number of uncompressed data bytes not extracted

    // Wig data extraction members
    private ArrayList<WigItem> wigItemList;  // array of Wig section items

    /*
    *   Constructor for Wig data block reader.
    *
    *   Parameters:
    *       fis - file input stream handle
    *       leafHitItem - R+ tree leaf hit item containing data block file location and hit status
    *       chromIDTree - B+ chromosome index tree returns chromosome ID's for names
    *       isLowToHigh - byte order is low to high if true; else high to low
    *       uncompressBufSize - byte size for decompression buffer; else 0 for uncompressed
    *
    * */
    public BigWigDataBlock(SeekableStream fis, RPTreeLeafNodeItem leafHitItem,
            HashMap<Integer, String> chromosomeMap, boolean isLowToHigh, int uncompressBufSize,
            CompressionUtils compressionUtils){
        this.leafHitItem = leafHitItem;
        this.chromosomeMap = chromosomeMap;
        this.isLowToHigh = isLowToHigh;

        fileOffset = this.leafHitItem.getDataOffset();
        leafDataSize = this.leafHitItem.geDataSize();
        byte[] buffer = new byte[(int) leafDataSize];

        // read Wig data block into a buffer
        try {
            fis.seek(fileOffset);
            fis.readFully(buffer);

            // decompress if necessary - the buffer size is 0 for uncompressed data
            // Note:  BBFile Table C specifies a decompression buffer size
            if(uncompressBufSize > 0)
                wigBuffer = compressionUtils.decompress(buffer, uncompressBufSize);
            else
                wigBuffer = buffer;    // use uncompressed read buffer directly
        }catch(IOException ex) {
             log.error("Error reading Wig section for leaf item ", ex);
             String error = String.format("Error reading Wig section for leaf item %d\n");
             throw new RuntimeException(error, ex);
        }

        // initialize unread data size
        remDataSize = wigBuffer.length;

        // use getWigData to extract data block items
    }

    /*
    *   Method reads all Wig data sections within the decompressed block buffer
    *   and returns those items in the chromosome selection region.
    *
    *   Parameters:
    *       selectionRegion - chromosome region for selecting Wig values
   *       contained - indicates selected data must be contained in selection region
    *           if true, else may intersect selection region
    *
    *   Returns:
    *      Wig sections in selected from the data block; else null for none selected.
    *
    * */
    public ArrayList<WigItem> getWigData(RPChromosomeRegion selectionRegion,
                                                boolean contained){

        wigItemList = new ArrayList<WigItem>();
        
        for(int index = 0; remDataSize > 0; ++index) {

            // extract items in the Wig data section
            // Note: A RuntimeException is thrown if wig section is not read properly
            BigWigSection wigSection = new BigWigSection(wigBuffer, chromosomeMap, isLowToHigh, leafHitItem);

            // get wig section items and section bytes read
            int sectionBytes = wigSection.getSectionData(selectionRegion, contained, wigItemList);

            // adjust remaining data block size
            remDataSize -= sectionBytes;
        }

        return wigItemList;
    }

     public void print() {

        log.debug("Wig section data referenced by leaf item ");

        for(int index = 0; index <= wigItemList.size(); ++index) {
            // BigWig sections print themselves
            wigItemList.get(index).print();
         }
    }


}
