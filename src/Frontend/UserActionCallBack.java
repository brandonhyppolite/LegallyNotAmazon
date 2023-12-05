package src.Frontend;

/**
 * The `UserActionCallBack` interface defines methods for performing actions and updating the user interface.
 */
public interface UserActionCallBack {
    /**
     * Saves data and refreshes the user interface.
     */
    void saveAndRefresh();

    /**
     * Saves data, refreshes the user interface, and performs additional actions based on the given ID.
     *
     * @param ID The ID associated with the action.
     */
    void saveAndRefresh(String ID);
}