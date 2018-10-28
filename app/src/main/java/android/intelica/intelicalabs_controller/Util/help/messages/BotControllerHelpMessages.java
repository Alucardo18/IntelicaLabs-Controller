package android.intelica.intelicalabs_controller.Util.help.messages;

public enum BotControllerHelpMessages {
    FORWARDS{
        @Override
        public String toString() {
            return "Mueve para adelante";
        }
    },
    BACKWARDS{
        @Override
        public String toString() {
            return "Mueve para atrás";
        }
    },
    ROTATE_LEFT{
        @Override
        public String toString() {
            return "Rota a la izquierda";
        }
    },
    ROTATE_RIGHT{
        @Override
        public String toString() {
            return "Rota a la derecha";
        }
    },
    BATTLE{
        @Override
        public String toString() {
            return "Modo batalla, hace que...";
        }
    },
    LINE{
        @Override
        public String toString() {
            return "Modo seguidor de lineas, hace que...";
        }
    },
    RANGER{
        @Override
        public String toString() {
            return "Modo ranger, hace que...";
        }
    },
    COLOR_MIXER{
        @Override
        public String toString() {
            return "Color mixer";
        }
    },
    HONK{
        @Override
        public String toString() {
            return "Toca un sonidito";
        }
    }
}
