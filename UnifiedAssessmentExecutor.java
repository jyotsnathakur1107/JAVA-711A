//Jyotsna Thakur
//UID-24BCS11053

abstract class AbstractAssessmentFlow {

    final void executeAssessment() {
        validate();
        prepare();
        evaluate();
        publishResult();
    }

    final void validate() {
        System.out.println("Validate");
    }

    final void prepare() {
        System.out.println("Prepare");
    }

    abstract void evaluate();

    void publishResult() {
        System.out.println("Result");
    }
}

interface AutoAssessment {
    default void evaluate() {
        System.out.println("Auto evaluate");
    }
}

interface ManualAssessment {
    default void evaluate() {
        System.out.println("Manual evaluate");
    }
}

public class UnifiedAssessmentExecutor extends AbstractAssessmentFlow
        implements AutoAssessment, ManualAssessment {

    private boolean isProctored;

    public UnifiedAssessmentExecutor(boolean isProctored) {
        this.isProctored = isProctored;
    }

    @Override
    public void evaluate() {
        if (isProctored) {
            ManualAssessment.super.evaluate();
        } else {
            AutoAssessment.super.evaluate();
        }
    }
}