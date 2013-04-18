#apple.laf.useScreenMenuBar for Macs, to put menu bar at top of screen
#-Xmx2000m indicates 1200 mb of memory, adjust number up or down as desired
#-Dproduction=true disables non-released and development features
#Script must be in the same directory as igv.jar
cd `dirname $0`
java -Dproduction=true -Dapple.laf.useScreenMenuBar=true -Xmx2000m -Djava.net.preferIPv4Stack=true -jar `dirname $0`/igv.jar $*
