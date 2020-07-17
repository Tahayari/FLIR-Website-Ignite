package utils.testCasesInfo;

public class LibraryPageInfo {
    private final String category;
    private final String createNewFolderWithBlankName_Test_title;
    private final String createNewFolderWithBlankName_Test_desc;
    private final String createNewFolderWithReallyLongName_Test_title;
    private final String createNewFolderWithReallyLongName_Test_desc;
    private final String createNewFolder_Test_title;
    private final String createNewFolder_Test_desc;
    private final String createFolderWithIllegalCharacters_Test_title;
    private final String createFolderWithIllegalCharacters_Test_desc;

    public LibraryPageInfo() {
        category = "LIBRARY_PAGE";
        createNewFolderWithBlankName_Test_title = "LIBRARY PAGE - createNewFolderWithBlankName_Test";
        createNewFolderWithBlankName_Test_desc = "Error message is displayed when the user creates a new folder that has a blank name";
        createNewFolderWithReallyLongName_Test_title = "LIBRARY PAGE - createNewFolderWithReallyLongName_Test";
        createNewFolderWithReallyLongName_Test_desc = "Error message is displayed when the user creates a new folder that has a more than 256 characters";
        createNewFolder_Test_title = "LIBRARY PAGE - createNewFolder_Test";
        createNewFolder_Test_desc = "Successfully create a new folder";
        createFolderWithIllegalCharacters_Test_title="LIBRARY PAGE - createFolderWithIllegalCharacters_Test_title";
        createFolderWithIllegalCharacters_Test_desc = "Error message is displayed if the name of the folder contains an illegal character";
    }

    public String getCategory() {
        return category;
    }

    public String getCreateNewFolderWithBlankName_Test_title() {
        return createNewFolderWithBlankName_Test_title;
    }

    public String getCreateNewFolderWithBlankName_Test_desc() {
        return createNewFolderWithBlankName_Test_desc;
    }

    public String getCreateNewFolderWithReallyLongName_Test_title() {
        return createNewFolderWithReallyLongName_Test_title;
    }

    public String getCreateNewFolderWithReallyLongName_Test_desc() {
        return createNewFolderWithReallyLongName_Test_desc;
    }

    public String getCreateNewFolder_Test_title() {
        return createNewFolder_Test_title;
    }

    public String getCreateNewFolder_Test_desc() {
        return createNewFolder_Test_desc;
    }

    public String getCreateFolderWithIllegalCharacters_Test_title() {
        return createFolderWithIllegalCharacters_Test_title;
    }

    public String getCreateFolderWithIllegalCharacters_Test_desc() {
        return createFolderWithIllegalCharacters_Test_desc;
    }
}
