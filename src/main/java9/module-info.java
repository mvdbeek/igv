module org.broad.igv {
    exports org.broad.igv;
    exports org.broad.igv.annotations;
    exports org.broad.igv.batch;
    exports org.broad.igv.bbfile;
    exports org.broad.igv.bigwig;
    exports org.broad.igv.cbio;
    exports org.broad.igv.charts;
    exports org.broad.igv.cli_plugin;
    exports org.broad.igv.cli_plugin.ui;
    exports org.broad.igv.das;
    exports org.broad.igv.data;
    exports org.broad.igv.data.cufflinks;
    exports org.broad.igv.data.expression;
    exports org.broad.igv.data.rnai;
    exports org.broad.igv.data.seg;
    exports org.broad.igv.dev;
    exports org.broad.igv.dev.api;
    exports org.broad.igv.dev.api.batch;
    exports org.broad.igv.dev.db;
    exports org.broad.igv.event;
    exports org.broad.igv.exceptions;
    exports org.broad.igv.feature;
    exports org.broad.igv.feature.basepair;
    exports org.broad.igv.feature.bionano;
    exports org.broad.igv.feature.dranger;
    exports org.broad.igv.feature.dsi;
    exports org.broad.igv.feature.genome;
    exports org.broad.igv.feature.genome.fasta;
    exports org.broad.igv.feature.tribble;
    exports org.broad.igv.feature.tribble.reader;
    exports org.broad.igv.ga4gh;
    exports org.broad.igv.gitools;
    exports org.broad.igv.gs;
    exports org.broad.igv.gs.dm;
    exports org.broad.igv.gs.atm;
    exports org.broad.igv.goby;
    exports org.broad.igv.gwas;
    exports org.broad.igv.lists;
    exports org.broad.igv.maf;
    exports org.broad.igv.methyl;
    exports org.broad.igv.mupit;
    exports org.broad.igv.peaks;
    exports org.broad.igv.plugin;
    exports org.broad.igv.plugin.mongovariant;
    exports org.broad.igv.plugin.mongocollab;
    exports org.broad.igv.prefs;
    exports org.broad.igv.repeats;
    exports org.broad.igv.renderer;
    exports org.broad.igv.sam;
    exports org.broad.igv.sam.cram;
    exports org.broad.igv.sam.cram.old;
    exports org.broad.igv.sam.lite;
    exports org.broad.igv.sam.mutreview;
    exports org.broad.igv.sam.reader;
    exports org.broad.igv.session;
    exports org.broad.igv.synteny;
    exports org.broad.igv.tdf;
    exports org.broad.igv.tools;
    exports org.broad.igv.tools.converters;
    exports org.broad.igv.tools.motiffinder;
    exports org.broad.igv.tools.parsers;
    exports org.broad.igv.tools.sort;
    exports org.broad.igv.tools.ui;
    exports org.broad.igv.track;
    exports org.broad.igv.ui;
    exports org.broad.igv.ui.action;
    exports org.broad.igv.ui.color;
    exports org.broad.igv.ui.commandbar;
    exports org.broad.igv.ui.dnd;
    exports org.broad.igv.ui.filefilters;
    exports org.broad.igv.ui.legend;
    exports org.broad.igv.ui.panel;
    exports org.broad.igv.ui.svg;
    exports org.broad.igv.ui.util;
    exports org.broad.igv.ui.util.download;
    exports org.broad.igv.util;
    exports org.broad.igv.util.blat;
    exports org.broad.igv.util.collections;
    exports org.broad.igv.util.converters;
    exports org.broad.igv.util.extview;
    exports org.broad.igv.util.encode;
    exports org.broad.igv.util.ftp;
    exports org.broad.igv.util.index;
    exports org.broad.igv.util.stream;
    exports org.broad.igv.util.stats;
    exports org.broad.igv.variant;
    exports org.broad.igv.variant.New;
    exports org.broad.igv.variant.util;
    exports org.broad.igv.variant.vcf;

    exports biz.source_code.base64Coder;

    opens org.broad.igv.cli_plugin to java.xml.bind;
    opens org.broad.igv.data to java.xml.bind;
    opens org.broad.igv.dev.db to java.xml.bind;
    opens org.broad.igv.feature.basepair to java.xml.bind;
    opens org.broad.igv.gwas to java.xml.bind;
    opens org.broad.igv.renderer to java.xml.bind;
    opens org.broad.igv.sam to java.xml.bind;
    opens org.broad.igv.session to java.xml.bind;
    opens org.broad.igv.tools.motiffinder to java.xml.bind;
    opens org.broad.igv.track to java.xml.bind;
    opens org.broad.igv.variant to java.xml.bind;
    
    requires AbsoluteLayout;
    requires ant;
//    requires batik.dom;
//    requires batik.svggen;
    requires commons.io;
    requires commons.math;
    requires goby.io.igv;
    requires com.google.common;
    requires gson;
    requires htsjdk;
    requires jargs;
    requires java.datatransfer;
    requires java.desktop;
    requires java.instrument;
    requires java.management;
    requires java.prefs;
    requires java.sql;
    requires java.xml;
    requires java.xml.bind;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
//    requires jdk.xml.dom;
    requires jfreechart;
    requires jgrapht;
    requires log4j;
    requires mongo.java.driver;
    requires na12878kb.utils;
//    requires log4j.api;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires swing.layout;
}
