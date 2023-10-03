package org.simple.engine.physics;

/**
 * Class encapsulating the timestep information.
 * <p>
 * A time step represents the elapsed time since the last update.
 *
 * @author William Bittle
 * @version 5.0.0
 * @since 1.0.0
 */
public class TimeStep {

  /**
   * The last elapsed time
   */
  protected double dt0;

  /**
   * The last inverse elapsed time
   */
  protected double invdt0;

  /**
   * The elapsed time
   */
  protected double dt;

  /**
   * The inverse elapsed time
   */
  protected double invdt;

  /**
   * The elapsed time ratio from the last to the current
   */
  protected double dtRatio;

  /**
   * Default constructor.
   *
   * @param dt the initial delta time in seconds; must be greater than zero
   * @throws IllegalArgumentException if dt is less than or equal to zero
   */
  public TimeStep(double dt) {
    if (dt <= 0.0) {
      throw new IllegalArgumentException("dt must be greater than 0");
    }

    this.dt = dt;
    this.invdt = 1.0 / dt;
    this.dt0 = this.dt;
    this.invdt0 = this.invdt;
    this.dtRatio = 1.0;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Step[DeltaTime=").append(this.dt)
        .append("|InverseDeltaTime=").append(this.invdt)
        .append("|PreviousDeltaTime=").append(this.dt0)
        .append("|PreviousInverseDeltaTime=").append(this.invdt0)
        .append("|DeltaTimeRatio=").append(this.dtRatio)
        .append("]");
    return sb.toString();
  }

  /**
   * Updates the current {@link TimeStep} using the new elapsed time.
   *
   * @param dt in delta time in seconds; must be greater than zero
   * @throws IllegalArgumentException if dt is less than or equal to zero
   */
  public void update(double dt) {
    if (dt <= 0.0) {
      throw new IllegalArgumentException("dt must be greater than 0");
    }

    this.dt0 = this.dt;
    this.invdt0 = this.invdt;
    this.dt = dt;
    this.invdt = 1.0 / dt;
    this.dtRatio = this.invdt0 * dt;
  }

  /**
   * Returns the elapsed time since the last time step in seconds.
   *
   * @return double
   */
  public double getDeltaTime() {
    return this.dt;
  }

  /**
   * Returns the inverse of the elapsed time (in seconds) since the last time step.
   *
   * @return double
   */
  public double getInverseDeltaTime() {
    return this.invdt;
  }

  /**
   * Returns the ratio of the last elapsed time to the current elapsed time.
   * <p>
   * This is used to cope with a variable time step.
   *
   * @return double
   */
  public double getDeltaTimeRatio() {
    return this.dtRatio;
  }

  /**
   * Returns the previous frame's elapsed time in seconds.
   *
   * @return double
   */
  public double getPreviousDeltaTime() {
    return this.dt0;
  }

  /**
   * Returns the previous frame's inverse elapsed time (in seconds).
   *
   * @return double
   */
  public double getPreviousInverseDeltaTime() {
    return this.invdt0;
  }
}