package templates;


public abstract class FillingDatabase {

    public final void FillData(Long customerId) {
        GetDataAndFill(customerId);
    }

    protected abstract void GetDataAndFill(Long customerId);

}
