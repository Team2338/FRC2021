package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.autoaim.AutoAim;

public class AutoAimTest extends SequentialCommandGroup {

    public AutoAimTest() {
        addCommands(
                new AutoAim()
        );
    }
}
