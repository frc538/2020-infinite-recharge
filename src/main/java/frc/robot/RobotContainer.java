/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.command.climb.ExtendCommand;
import frc.robot.command.climb.LiftCommand;
import frc.robot.command.climb.RetractCommand;
import frc.robot.command.collect.CollectCommand;
import frc.robot.command.colorWheel.ToggleHeightCommand;
import frc.robot.command.colorWheel.ToggleSpinCommand;
import frc.robot.command.drive.DriveCommand;
import frc.robot.command.shooter.ShootCommand;
import frc.robot.subsystem.CollectorSubsystem;
import frc.robot.subsystem.ColorWheelSubsystem;
import frc.robot.subsystem.ClimberSubsystem;
import frc.robot.subsystem.ShooterSubsystem;
import frc.robot.subsystem.drive.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Joystick joystick = new Joystick(Constants.JOYSTICK_DRIVER_USB_ID);
  private final XboxController xbox = new XboxController(Constants.XBOX_DRIVER_USB_ID);
  private final Joystick nukeButton = new Joystick(Constants.BUTTON_USB_ID);
  private final DriveSubsystem drive = new DriveSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  private final CollectorSubsystem collector = new CollectorSubsystem();
  private final ClimberSubsystem climber = new ClimberSubsystem();
  private final ColorWheelSubsystem colorWheel = new ColorWheelSubsystem();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drive.setDefaultCommand(new DriveCommand(drive, joystick));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton shoot = new JoystickButton(xbox, Constants.BUTTON_ID.SHOOTER);
    shoot.whenHeld(new ShootCommand(shooter, true));
    shoot.whenReleased(new ShootCommand(shooter, false));

    JoystickButton collect = new JoystickButton(xbox, Constants.BUTTON_ID.COLLECTOR);
    collect.whenReleased(new CollectCommand(collector));

    JoystickButton key = new JoystickButton(nukeButton, Constants.BUTTON_ID.KEY);
    JoystickButton button = new JoystickButton(nukeButton, Constants.BUTTON_ID.RED_BUTTON);

    key.and(button).whenActive(new ExtendCommand(climber));
    key.whenInactive(new RetractCommand(climber));

    JoystickButton lift = new JoystickButton(xbox, Constants.BUTTON_ID.LIFT);
    lift.whenHeld(new LiftCommand(climber, true));
    lift.whenReleased(new LiftCommand(climber, false));

    JoystickButton raiseColor = new JoystickButton(xbox, Constants.BUTTON_ID.COLOR_TOGGLE);
    raiseColor.whenPressed(new ToggleHeightCommand(colorWheel));

    JoystickButton stageOne = new JoystickButton(xbox, Constants.BUTTON_ID.STAGE_ONE);
    JoystickButton stageTwo = new JoystickButton(xbox, Constants.BUTTON_ID.STAGE_TWO);
    stageOne.whenPressed(new ToggleSpinCommand(colorWheel));
    stageTwo.whenPressed(new ToggleSpinCommand(colorWheel));


  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }

  public void init() {

    drive.init();
  }
}
