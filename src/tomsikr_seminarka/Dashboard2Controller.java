package tomsikr_seminarka;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Dashboard2Controller implements Initializable {
    public String inputUserName;
    public String inputPassword;
    public String loggedInName;
    public String loggedInPermission;
    public int loggedInID;
    public boolean loggedIn;
    public TextField usernameTextfield;
    public PasswordField Passwordfield;
    public Label loggedInStatusLabel;
    public Button logoutButton;
    public Button loginButton;
    public TextField loggedInPrintBox;
    public TextField loggedInNamePrintBox;
    public TextField loggedInPermissionPrintBox;
    public Tab animalsTab;
    public Tab usersTab;
    public Tab debugTab;

    public int highestAnimalIndex;
    public int highestUserIndex;
    public int lastRow;
    public static String animalsDBPath = "./data/animals.txt";
    public static String usersConfPath = "./data/users.conf";

    //Animals TableView
    public TableColumn<Animals, Integer> animalColumn_id;
    public TableColumn<Animals, String> animalColumn_species;
    public TableColumn<Animals, String> animalColumn_category;
    public TableColumn<Animals, String> animalColumn_name;
    public TableColumn<Animals, String> animalColumn_age;
    public TableColumn<Animals, String> animalColumn_health;
    public TableColumn<Animals, String> animalColumn_weight;
    public TableColumn<Animals, String> animalColumn_sex;
    public TableColumn<Animals, String> animalColumn_notes;
    public TableView<Animals> animalsTable;
    public Button addNewAnimalRecordButton;
    public Button deleteSelectedAnimalRowButton;

    //Users tableView
    public TableColumn<Users, String> usersColumn_name;
    public TableColumn<Users, String> usersColumn_password;
    public TableColumn<Users, String> usersColumn_permission;
    public TableColumn<Users, String> usersColumn_age;
    public TableColumn<Users, String> usersColumn_phoneNumber;
    public TableColumn<Users, Integer> usersColumn_ID;
    public TableView<Users> usersTable;
    public Button deleteSelectedUserRowButton;
    public TextField newUserNameTextField;
    public TextField newUserPasswordTextField;
    public TextField newUserPhonenumberTextField;
    public ToggleGroup newUserPermsRadioButtonGroup;
    public TextField newUserAgeTextField;
    public TextField newUserIDTextField;
    public RadioButton doctorRadioButton;
    public Button changeMyPassword;
    public Button changeUserPasswordAdmin;
    public TabPane tabPane;


    //Arraylists
    private ArrayList<Users> usersList = new ArrayList<>();
    private ArrayList<Animals> animalsList = new ArrayList<Animals>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Functions.checkFirstLaunch(usersConfPath,animalsDBPath);
        checkLoggedIn(loggedIn);
        usernameTextfield.setText("");
        Passwordfield.setText("");
        Functions.readUsersFileToArrayList(usersList, usersConfPath);
        Functions.readAnimalsFileToArrayList(animalsList, animalsDBPath);

        //Tables have to be populated on launch, otherwise they brokey
        populateAnimalTableView();
        populateUsersTableView();

        //Sets ID into a textbox for creating a new user (current highest ID + 1)
        setHighestUserID();
    }

    public void loginButtonPressed(ActionEvent actionEvent) {
        inputUserName = usernameTextfield.getText();
        inputPassword = Passwordfield.getText();
        if (doNameAndPasswordMatch(inputUserName,inputPassword,usersList)){
            //System.out.println("DEBUG WARNING - login match");
            loggedInStatusLabel.setText("Logged in");
            loggedIn = true;
            logoutButton.setDisable(false);
            loginButton.setDisable(true);
            loggedInPrintBox.setText(Boolean.toString(loggedIn));
            loggedInNamePrintBox.setText(loggedInName);
            loggedInPermissionPrintBox.setText(loggedInPermission);
            checkLoggedIn(loggedIn);
        }else{
            loggedInStatusLabel.setText("Invalid username or password. Please try again");
        }
        usernameTextfield.clear();
        Passwordfield.clear();
        if(loggedIn == true) {
            usernameTextfield.setDisable(true);
            Passwordfield.setDisable(true);
        }
    }

    public void logoutButtonPressed(ActionEvent actionEvent) {
        loggedIn = false;
        loggedInName = "";
        loggedInPermission = "";
        loggedInPrintBox.setText(Boolean.toString(loggedIn));
        loggedInNamePrintBox.setText(loggedInName);
        loggedInPermissionPrintBox.setText(loggedInPermission);
        loggedInStatusLabel.setText("Logged out");
        usernameTextfield.clear();
        Passwordfield.clear();
        usernameTextfield.setDisable(false);
        Passwordfield.setDisable(false);

        checkLoggedIn(loggedIn);
    }

    //Button in debugg tab
    public void printUsersIntoConsole(ActionEvent actionEvent) {
        Functions.printArrayListUsers(usersList);
    }

    //Checking entered username and password against data in a loaded Users arraylist
    public boolean doNameAndPasswordMatch(String enteredName, String enteredPassword, ArrayList<Users> usersList) {
        boolean result = false;
        boolean valid = false;
        String hashedPassword;
        //System.out.println("Checking login info");
        //System.out.println("DEBUG WARNING - Users array list size: " + usersList.size());

        for (int i = 0; i < usersList.size(); i++) {
            int id = usersList.get(i).id;
            String name = usersList.get(i).name.trim();
            String password = usersList.get(i).password.trim();
            String permission = usersList.get(i).permission.trim();

            /*
            System.out.println();
            System.out.println("********************************************");
            System.out.println("DEBUG WARNING - Checking user on index " + i);
			System.out.println("DEBUG WARNING - tempName "+ name);
			System.out.println("DEBUG WARNING - Entered name "+ enteredName);
			System.out.println("DEBUG WARNING - tempPassword "+ password);
			System.out.println("DEBUG WARNING - Entered password "+ enteredPassword);
             */
            hashedPassword = Functions.getHash(enteredPassword, "MD5").toUpperCase();
            //System.out.println("DEBUG WARNING - Entered hashed password "+ hashedPassword);

            if ((enteredName.equals(name)) && (valid == false)) {
                //System.out.println("DEBUG WARNING - Names match");
                if (password.equals(hashedPassword)) {
                    valid = true;
                    //System.out.println("DEBUG WARNING - Passwords match");
                    //System.out.println("DEBUG WARNING - valid " + valid);
                    loggedInPermission = permission;
                    loggedInName = name;
                    loggedInID = id;
                    System.out.println("DEBUG WARNING - loggedInID: " + loggedInID);
                    break;
                } else {
                    //System.out.println("DEBUG WARNING - Passwords do not match");
                    valid = false;
                    //System.out.println("DEBUG WARNING - valid " + valid);
                }
            }else{
                //System.out.println("DEBUG WARNING - Names do not match");
            }
        }

        //System.out.println("DEBUG WARNING - valid for return " + valid);
        if (valid == true) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    //Animals table
    private void populateAnimalTableView(){
        //Create table
        animalColumn_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        animalColumn_species.setCellValueFactory(new PropertyValueFactory<>("Species"));
        animalColumn_category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        animalColumn_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        animalColumn_age.setCellValueFactory(new PropertyValueFactory<>("Age"));
        animalColumn_health.setCellValueFactory(new PropertyValueFactory<>("Health"));
        animalColumn_weight.setCellValueFactory(new PropertyValueFactory<>("Weight"));
        animalColumn_sex.setCellValueFactory(new PropertyValueFactory<>("Sex"));
        animalColumn_notes.setCellValueFactory(new PropertyValueFactory<>("Notes"));

        //Populate table
        for(int i = 0; i<animalsList.size(); i++) {
            Animals animal = new Animals(animalsList.get(i).id,animalsList.get(i).species,animalsList.get(i).category,animalsList.get(i).name,animalsList.get(i).age, animalsList.get(i).health,animalsList.get(i).weight,animalsList.get(i).sex,animalsList.get(i).notes);
            animalsTable.getItems().add(animal);
        }
        editAnimalTableView();
    }

    //Allows table edits by double clicking a cell
    private void editAnimalTableView(){
        animalsTable.setEditable(true);

        //THIS ONLY WORKS FOR STRINGS
        /*animalColumn_id.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_id.setOnEditCommit(e-> {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
        });*/

        animalColumn_species.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_species.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setSpecies(e.getNewValue());
        });

        animalColumn_category.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_category.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCategory(e.getNewValue());
        });

        animalColumn_name.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_name.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        animalColumn_age.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_age.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setAge(e.getNewValue());
        });

        animalColumn_health.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_health.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setHealth(e.getNewValue());
        });

        animalColumn_weight.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_weight.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setWeight(e.getNewValue());
        });
        animalColumn_sex.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_sex.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setSex(e.getNewValue());
        });
        animalColumn_notes.setCellFactory(TextFieldTableCell.forTableColumn());
        animalColumn_notes.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setNotes(e.getNewValue());
        });
    }

    public void printAnimalsIntoConsole(ActionEvent actionEvent) {
        Functions.printArrayListAnimals(animalsList);
    }

    //Clears table and arraylist, read data from data file and populate both
    public void refreshAnimalTable(){
        animalsTable.getItems().clear();
        animalsList.clear();
        Functions.readAnimalsFileToArrayList(animalsList, animalsDBPath);
        populateAnimalTableView();
    }

    //Saves the entire table into into the data file
    public void saveAnimalChanges(ActionEvent actionEvent) {
        System.out.println("DEBUG WARNING - saving animal changes");
        //List <List<String>> arrList = new ArrayList<>();
        int id = 0;
        String species  = "";
        String category  = "";
        String name  = "";
        String age  = "";
        String health  = "";
        String weight  = "";
        String sex  = "";
        String notes  = "";
        Animals animal = new Animals(id,species,category,name,age, health,weight,sex,notes);

        animalsList.clear();
        for(int i = 0; i < animalsTable.getItems().size(); i++) {
            animal = animalsTable.getItems().get(i);
            /*System.out.println(i + ". animal ID: " + animal.getId());
            System.out.println(i + ". animal species: " + animal.getSpecies());
            System.out.println(i + ". animal category: " + animal.getCategory());
            System.out.println(i + ". animal name: " + animal.getName());*/
            animalsList.add(animal);
        }
        Functions.writeAnimalArrayListIntoFile(animalsList, animalsDBPath);
        addNewAnimalRecordButton.setDisable(false);
    }

    //Creates new empty row in the table, fills ID
    public void addNewAnimalRecord(ActionEvent actionEvent) {
        int lastRow = animalsTable.getItems().size();
        System.out.println("DEBUG WARNING - animals lastRow: " + lastRow);
        if(animalsList.size() == 0){
            highestAnimalIndex = 0;
        }else {
            highestAnimalIndex = animalsList.get(animalsList.size() - 1).id;
        }
        System.out.println("DEBUG WARNING - highest animal index" + highestAnimalIndex);
        System.out.println("DEBUG WARNING - row count before adding a new one " + lastRow);
        animalsTable.getItems().add(new Animals((highestAnimalIndex + 1),null,null,null,null, null,null,null,null));
        addNewAnimalRecordButton.setDisable(true);
    }

    //Deletion of a selected row
    public void deleteSelectedAnimalRecord(ActionEvent actionEvent) {
        deleteSelectedAnimalRowButton.setOnAction(e -> {
            Animals selectedItem = animalsTable.getSelectionModel().getSelectedItem();
            animalsTable.getItems().remove(selectedItem);
        });
    }

    //Users table
    private void populateUsersTableView(){
        usersColumn_ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        usersColumn_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        usersColumn_password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        usersColumn_permission.setCellValueFactory(new PropertyValueFactory<>("Permission"));
        usersColumn_age.setCellValueFactory(new PropertyValueFactory<>("Age"));
        usersColumn_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));

        for(int i = 0; i<usersList.size(); i++){
            Users user = new Users(usersList.get(i).getId(),usersList.get(i).getName(),usersList.get(i).getPassword(),usersList.get(i).getPermission(),usersList.get(i).getAge(),usersList.get(i).getPhoneNumber());
            usersTable.getItems().add(user);
        }
        usersTable.getColumns().remove(usersColumn_password);

        editUsersTableView();
    }

    private void editUsersTableView(){
        usersTable.setEditable(true);

        /*usersColumn_name.setCellFactory(TextFieldTableCell.forTableColumn());
        usersColumn_name.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });*/

        /*usersColumn_password.setCellFactory(TextFieldTableCell.forTableColumn());
        usersColumn_password.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });*/

        usersColumn_permission.setCellFactory(TextFieldTableCell.forTableColumn());
        usersColumn_permission.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPermission(e.getNewValue());
        });

        usersColumn_age.setCellFactory(TextFieldTableCell.forTableColumn());
        usersColumn_age.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setAge(e.getNewValue());
        });

        usersColumn_phoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        usersColumn_phoneNumber.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPhoneNumber(e.getNewValue());
        });
    }

    public void refreshUsersTable(){
        usersTable.getItems().clear();
        usersList.clear();
        Functions.readUsersFileToArrayList(usersList, usersConfPath);
        populateUsersTableView();
        setHighestUserID();
    }

    public void addNewUser(ActionEvent actionEvent) {
        Functions.checkFirstLaunch(usersConfPath,animalsDBPath);
        setHighestUserID();
        System.out.println("DEBUG WARNING - row count before adding a new one " + lastRow);

        int id = Integer.parseInt(newUserIDTextField.getText());

        String name = newUserNameTextField.getText();
        boolean validName = Functions.isUsernameValid(name, usersList);

        String password = newUserPasswordTextField.getText();
        password = Functions.getHash(password, "MD5").toUpperCase();

        String age = newUserAgeTextField.getText();

        String permission = "";

        int permissionIndex = newUserPermsRadioButtonGroup.getToggles().indexOf(newUserPermsRadioButtonGroup.getSelectedToggle());
        switch(permissionIndex){
            case 0:{
                permission = "administrator";
                break;
            }
            case 1:{
                permission = "doctor";
                break;
            }
        }
        String phoneNumber = newUserPhonenumberTextField.getText();

        //Checking if entered values are valid
        if((validName == true)) {
            System.out.println("DEBUG WARNING - usersList size before adding: " + usersList.size());
            Users user = new Users(id, name, password, permission, age, phoneNumber);
            usersList.add(user);
            System.out.println("DEBUG WARNING - usersList size after adding: " + usersList.size());
            usersTable.getItems().add(user);

            Functions.printArrayListUsers(usersList);
            Functions.writeUsersArrayListIntoFile(usersList, usersConfPath);

            refreshUsersTable();
        }else{
            JOptionPane.showMessageDialog(null,"Duplicate username, please try again");
        }
    }

    public void deleteSelectedUserRow(ActionEvent actionEvent) {
        deleteSelectedUserRowButton.setOnAction(e -> {
            Users selectedItem = usersTable.getSelectionModel().getSelectedItem();
            usersTable.getItems().remove(selectedItem);
        });
    }

    //Changes password of a logged user
    public void changeMyPasswordButtonPressed(ActionEvent actionEvent) {
        changeUserPassword(usersList, loggedInID);
    }

    public void checkLoggedIn(boolean loggedIn){
        if(loggedIn == false){
            loggedInPermission = "";
            loginButton.setDisable(false);
            logoutButton.setDisable(true);
            animalsTab.setDisable(true);
            usersTab.setDisable(true);
            debugTab.setDisable(true);
            changeMyPassword.setDisable(true);
        }
        if(loggedIn == true){
            logoutButton.setDisable(false);
            loginButton.setDisable(true);
            if(loggedInPermission.equals("administrator")){
                debugTab.setDisable(false);
                usersTab.setDisable(false);
            }
            animalsTab.setDisable(false);
            loginButton.setDisable(true);
            logoutButton.setDisable(false);
            changeMyPassword.setDisable(false);
        }
        //tabPane.getTabs().remove(debugTab);
    }

    public void setHighestUserID(){
        doctorRadioButton.setSelected(true);
        lastRow = usersTable.getItems().size();
        if(lastRow == 0){
            highestUserIndex = 0;
        }else {
            highestUserIndex = usersList.get(usersList.size() - 1).id;
        }
        newUserIDTextField.setText(String.valueOf(highestUserIndex+1));
        //System.out.println("DEBUG WARNING - new user ID: " + newUserIDTextField.getText());
    }

    //Changes password of a user with a given ID
    public void changeUserPassword(ArrayList<Users> usersList, int id){
        //System.out.println("DEBUG WARNING - loggedInID: " + id);
        String oldPassword = "";
        String newPassword = "";
        oldPassword = usersList.get(id-1).password;
        //System.out.println("DEBUG WARNING - current password " + oldPassword);
        try {
            do {
                newPassword = JOptionPane.showInputDialog(null, "Enter new password");
                System.out.println(newPassword);
                if(newPassword.equals(null)){
                    break;
                }else{
                    newPassword = Functions.getHash(newPassword,"MD5").toUpperCase();
                    //System.out.println("New hashed password " + newPassword);
                }
                if((newPassword).equals(oldPassword)) {
                    JOptionPane.showMessageDialog(null,"New password can not be the same as old password. Please try again.");
                }
            } while ((newPassword).equals(oldPassword));

            for(Users user : usersList) {
                if(user.getId() == id) {
                    user.setPassword(newPassword);
                }
            }
            JOptionPane.showMessageDialog(null,"Password has been changed for user " + usersList.get(id-1).name);
        }catch(NullPointerException e){
            //e.printStackTrace();
        }

        System.out.println();
        //Functions.printArrayListUsers(usersList);
        Functions.writeUsersArrayListIntoFile(usersList,usersConfPath);
        refreshUsersTable();
    }

    //Input of what ID user's password to change
    public void changeUserPasswordAdminPressed(ActionEvent actionEvent) {
        int id = 0;
        String input = "";
        String string;
        try{
            do {
                input = JOptionPane.showInputDialog("Enter ID of the user you want to change, numbers only");
                if (input.matches("^[0-9]*$")) {
                    string = input;
                    System.out.println("Name "+string);
                } else {
                    System.out.println("Enter ID of the user you want to change, numbers only");
                }
            } while (!input.matches("^[0-9]*$"));
            id = Integer.valueOf(input);
        }catch(NullPointerException e){
            //e.printStackTrace();
        }
        changeUserPassword(usersList, id);
        //System.out.println("DEBUG WARNING - user ID for changing password: " + input);
    }

    //Debug method, prints current working directory into console
    public void printAnimalPath(ActionEvent actionEvent) {
        File animalFile = new File(animalsDBPath);
        System.out.println(animalFile.getAbsolutePath());
        System.out.println(System.getProperty("user.dir"));
    }

    public void saveUsersTableChanges(ActionEvent actionEvent) {
        System.out.println("DEBUG WARNING - saving animal changes");
        //List <List<String>> arrList = new ArrayList<>();
        int id = 0;
        String name = "";
        String password = "";
        String permission = "";
        String age = "";
        String phoneNumber = "";
        Users user = new Users(id, name, password, permission, age, phoneNumber);

        usersList.clear();
        for(int i = 0; i < usersTable.getItems().size(); i++) {
            user = usersTable.getItems().get(i);
            /*System.out.println(i + ". animal ID: " + animal.getId());
            System.out.println(i + ". animal species: " + animal.getSpecies());
            System.out.println(i + ". animal category: " + animal.getCategory());
            System.out.println(i + ". animal name: " + animal.getName());*/
            usersList.add(user);
        }
        Functions.writeUsersArrayListIntoFile(usersList, usersConfPath);
        addNewAnimalRecordButton.setDisable(false);

        refreshUsersTable();
    }
}
