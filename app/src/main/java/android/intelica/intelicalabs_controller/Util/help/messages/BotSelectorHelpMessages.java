package android.intelica.intelicalabs_controller.Util.help.messages;

public enum BotSelectorHelpMessages {

    FILL_ROBOT_LIST{
        @Override
        public String toString() {
            return "Este botón llena la lista con todos los dispositivos bluetooth" +
                    "asociados";
        }
    },
    ROBOT_LIST{
        @Override
        public String toString() {
            return "En esta lista debes elegir a tu robot para poder conectarte";
        }
    },
    START_CONTROLLER{
        @Override
        public String toString() {
            return "Con este boton se abre el controlador del robot";
        }
    },
    DISCONNECT{
        @Override
        public String toString() {
            return "Para desconectarte de tu robot, pulsa este botón";
        }
    }
}
