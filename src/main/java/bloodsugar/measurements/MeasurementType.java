package bloodsugar.measurements;

import javax.persistence.Embeddable;

@Embeddable
public enum MeasurementType {

    BEFORE_BREAKFAST, BEFORE_LUNCH, BEFORE_DINNER, ONE_HOUR_AFTER_BREAKFAST, ONE_HOUR_AFTER_LUNCH, ONE_HOUR_AFTER_DINNER
}
