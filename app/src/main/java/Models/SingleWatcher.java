package Models;

/**
 * Created by SAYED on 4/26/2016.
 */
public class SingleWatcher
{
    private String title;
    private String isAccepted;

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }


    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public SingleWatcher(String title,String isAccepted)
    {
        this.title = title;
        this.isAccepted = isAccepted;
    }

}
