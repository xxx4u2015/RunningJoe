package efficom.runningjoe.services;

/**
 * The available sound files.
 */
public enum RunningJoeSound
{
    CLICK( "sounds/click.wav" );

    private final String fileName;

    private RunningJoeSound( String fileName )
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
}