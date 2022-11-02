package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensor extends SubsystemBase {
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    /**
     * Get the most likely color. Works best when within 2 inches and perpendicular to surface of interest.
     * @return Color enum of the most likely color, including unknown if the minimum threshold is not met
     */
    public Color getColor() {
        return m_colorSensor.getColor();
    }

    /**
     * Get the most likely color, then ignore and return false
     * @return Will always return false
     */
    public boolean seeBlueBall() {
        Color color = m_colorSensor.getColor();
        return false;
    }
}
