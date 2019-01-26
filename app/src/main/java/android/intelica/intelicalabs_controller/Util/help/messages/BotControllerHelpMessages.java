package android.intelica.intelicalabs_controller.Util.help.messages;

public enum BotControllerHelpMessages {
    FORWARDS {
        @Override
        public String toString() {
            return "Manten presionado para mover el robot hacia adelante";
        }
    },
    BACKWARDS {
        @Override
        public String toString() {
            return "Manten presionado para mover el robot hacia atrás";
        }
    },
    ROTATE_LEFT {
        @Override
        public String toString() {
            return "Manten presionado para rotar el robot hacia la izquierda";
        }
    },
    ROTATE_RIGHT {
        @Override
        public String toString() {
            return "Manten presionado para rotar el robot hacia la derecha";
        }
    },
    BATTLE {
        @Override
        public String toString() {
            return "Al presionar esta funcion el robot entra en modo de batalla autonomo\neste buscara el objetivo mas cercano para atacarlo";
        }
    },
    LINE {
        @Override
        public String toString() {
            return "Al presionar esta funcion el robot entra en modo de seguidor de linea autonomo\neste seguira una linea usando los sensores inferiores";
        }
    },
    RANGER {
        @Override
        public String toString() {
            return "Al presionar esta funcion el robot entra en modo explorador autonomo\neste esquivara los obstaculos que se le presenten ";
        }
    },
    COLOR_MIXER {
        @Override
        public String toString() {
            return "Al acceder al menu de \"Color Mixer\".\npodras elegir el color de iluminacion LED de tu robot";
        }
    },
    HONK {
        @Override
        public String toString() {
            return "Toca un sonidito";
        }
    }
}
