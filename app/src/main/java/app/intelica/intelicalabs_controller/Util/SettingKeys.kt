package app.intelica.intelicalabs_controller.Util

// Directions
val forward = SettingKey("forwards", "FORWARD")
val backward = SettingKey("backwards", "BACKWARD")
val clockwise = SettingKey("clockwise", "RIGHT")
val counterClockwise = SettingKey("counter_clockwise", "LEFT")
val stop = SettingKey("stop", "STOP")

//Modes
val battle = SettingKey("battle", "BATTLE")
val line = SettingKey("line", "LINE")
val ranger = SettingKey("ranger", "RANGER")

//Sound
val honk = SettingKey("honk", "HONK")

//Indicators
val startIndicator = SettingKey("start_char", "%")
val stopIndicator = SettingKey("stop_char", "\n")

data class SettingKey(val key: String, val default: String)