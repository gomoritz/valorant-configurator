package cli

import com.xenomachina.argparser.ArgParser
import java.io.File

class CommandLineOptions(parser: ArgParser) {
    val configurationFile by parser.storing(
        "--configuration-file", "-C",
        help = "The file from which the Valorant settings are read or to which they are written."
    ) { File(this) }

    val mode by parser.mapping(
        "--read" to Mode.READ,
        "--write" to Mode.WRITE,
        help = "The action to be executed by the configurator"
    )

    val instant by parser.flagging(
        "--instant", "-I",
        help = "Whether to skip the initial delay and start the configurator instantly"
    )
}