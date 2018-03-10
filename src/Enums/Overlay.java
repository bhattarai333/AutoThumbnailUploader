package Enums;

public enum Overlay {
    DEFAULT_OVERLAY(){
        @Override
        public String toString() {
            return "Default Overlay";
        }
    },
    GOOSHI_OVERLAY(){
        @Override
        public String toString() {
            return "Gooshi Gaming Overlay";
        }
    },
}
