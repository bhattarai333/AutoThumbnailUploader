import java.awt.image.BufferedImage;

public enum Overlay {
    DEFAULT_OVERLAY(){
        @Override
        public String toString() {
            return "Default Overlay";
        }

        public BufferedImage createFrame(OverlayData OD){
            SmashOverlay sf = new SmashOverlay();
            return sf.createFrame(OD);
        }
    },
    GOOSHI_OVERLAY(){
        @Override
        public String toString() {
            return "Gooshi Gaming Overlay";
        }

        public BufferedImage createFrame(OverlayData OD){
            GooshiOverlay gf = new GooshiOverlay();
            return gf.createFrame(OD);
        }
    },
}
