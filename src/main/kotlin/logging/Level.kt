package logging

enum class Level(val color: Color) {
    DEBUG(Color.GREEN_BRIGHT),
    INFO(Color.BLUE_BRIGHT),
    WARN(Color.YELLOW_BRIGHT),
    ERROR(Color.RED_BOLD_BRIGHT)
}