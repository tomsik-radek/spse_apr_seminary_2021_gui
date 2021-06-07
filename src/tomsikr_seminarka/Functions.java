package tomsikr_seminarka;

import javax.swing.*;
import java.io.*;

import java.nio.file.Path;
import java.util.ArrayList;

public class Functions {
    public static String adminPhoneNumber;

    //Reads file line by line, segments it using commas and fills the arraylist
    public static void readUsersFileToArrayList(ArrayList<Users> usersList, String usersConfPath) {
        usersList.clear();
        if(new File(usersConfPath).exists()) {
            //System.out.println("DEBUG WARNING - file users.conf exists");
            try {
                BufferedReader bfr = new BufferedReader(new FileReader(usersConfPath));
                String line = bfr.readLine();
                while((line != null) && (!line.isEmpty())) {
                    int id = 0;
                    String name = "";
                    String password = "";
                    String permission = "";
                    String age = "";
                    String phoneNumber = "";

                    if(line.contains(",")) {
                        String parts[] = line.split(",");
                        id = Integer.parseInt(parts[0]);
                        name = parts[1].trim();
                        password = parts[2].trim();
                        password = password.toUpperCase();
                        permission = parts[3].trim();
                        age = parts[4].trim();
                        if(permission.equals("administrator")) {
                            phoneNumber = parts[5].trim();
                            adminPhoneNumber = phoneNumber;
                        }else {
                            phoneNumber = "";
                        }
                    }
                    Users temp = new Users(id,name,password,permission,age,phoneNumber);
					/*System.out.println("Name: " + name);
					System.out.println("Permission: " + permission);
					System.out.println("Password: " + password);*/

                    usersList.add(temp);
                    //System.out.println("DEBUG WARNING - User added");

                    line = bfr.readLine();
                }
                bfr.close();
            }catch(IOException e){
                e.printStackTrace();
            }
            //System.out.println("DEBUG WARNING - All users loaded from config to arraylist");
        }
    }

    //Deletes the file and then fills it with data from arraylist
    public static void writeUsersArrayListIntoFile(ArrayList<Users> usersList, String usersConfPath){
        File fileUser = new File(usersConfPath);
        if(fileUser.exists()) {
            fileUser.delete();
        }

        int id;
        String  name, password, permission, age, phoneNumber;

        for(int i = 0; i<usersList.size(); i++) {
            id = usersList.get(i).id;

            name = usersList.get(i).name;
            if(name == null){
                name = "";
            }

            password = usersList.get(i).password;
            if(password == null){
                password = "";
            }

            permission = usersList.get(i).permission;
            if(permission == null){
                permission = "";
            }

            age = usersList.get(i).age;
            if(age == null){
                age = "";
            }

            phoneNumber = usersList.get(i).phoneNumber;
            if(phoneNumber == null){
                phoneNumber = "";
            }


            String toAppend = id + ", " + name +  ", " + password +  ", " + permission + ", " + age + ", " + phoneNumber;
            appendStringToTextFile(usersConfPath, toAppend);
        }
    }

    public static void readAnimalsFileToArrayList(ArrayList<Animals> animalsList, String animalsDBPath) {
        if(new File(animalsDBPath).exists()) {
            try {
                BufferedReader bfr = new BufferedReader(new FileReader(animalsDBPath));
                String line = bfr.readLine();
                while((line != null) && (!line.isEmpty())) {
                    int id = 0;
                    String species = "";
                    String category = "";
                    String name = "";
                    String age = "";
                    String health = "";
                    String weight = "";
                    String sex = "";
                    String notes = "";

                    //System.out.println("DEBUG WARNING - got this far 1");
                    if(line.contains(",")) {
                        //System.out.println("DEBUG WARNING - line " + line);
                        String parts[] = line.split(",");
                        //System.out.println("DEBUG WARNING - parts[].length " + parts.length);
                        id = Integer.parseInt(parts[0].trim());
                        species = parts[1].trim();
                        category = parts[2].trim();
                        name = parts[3].trim();
                        age = parts[4].trim();
                        health = parts[5].trim();
                        weight = parts[6].trim();
                        sex = parts[7].trim();
                        notes = parts[8].trim();
                    }

                    Animals temp = new Animals(id,species,category,name,age,health,weight,sex,notes);
                    animalsList.add(temp);
                    //System.out.println("DEBUG WARNING - animal added");

                    line = bfr.readLine();
                }
                bfr.close();
            }catch(IOException e){
                e.printStackTrace();
            }
            //System.out.println("DEBUG WARNING - All animal records loaded from config to arraylist");
        }
    }

    public static void writeAnimalArrayListIntoFile(ArrayList<Animals> animalsList, String animalsDBPath){
        File fileAnm = new File(animalsDBPath);
        if(fileAnm.exists()) {
            fileAnm.delete();
        }

        int id;
        String  age, weight, species, category, name, health, sex, notes;

        for(int i = 0; i<animalsList.size(); i++) {
            id = animalsList.get(i).id;
            species = animalsList.get(i).species;
            if(species == null){
                species = "";
            }
            category = animalsList.get(i).category;
            if(category == null){
                category = "";
            }
            name = animalsList.get(i).name;
            if(name == null){
                name = "";
            }
            age = animalsList.get(i).age;
            if(age == null){
                age = "";
            }
            health = animalsList.get(i).health;
            if(health == null){
                health = "";
            }
            weight = animalsList.get(i).weight;
            if(weight == null){
                weight = "";
            }
            sex = animalsList.get(i).sex;
            if(sex == null){
                sex = "";
            }
            notes = animalsList.get(i).notes;
            if(notes == null){
                notes = "";
            }

            String toAppend = id + ", " + species +  ", " + category +  ", " + name + ", " + age + ", " + health + ", " + weight + ", " + sex + ", " + notes;
            appendStringToTextFile(animalsDBPath, toAppend);
        }
    }

    //Hashes password, current usage with paramemeter "MD5"
    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();

        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Appends string to file - writing without rewriting
    public static void appendStringToTextFile(String path, String toAppend) {
        try(FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(toAppend);
            //System.out.println("DEBUG WARNING - Input " + toAppend + " written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printArrayListUsers(ArrayList<Users> usersList) {
        System.out.println();
        System.out.println("Listing all loaded user data");
        for(int i = 0; i<usersList.size(); i++) {
            System.out.println("i: " + i + " - " + usersList.get(i).name);
            System.out.println("i: " + i + " - " + usersList.get(i).password);
            System.out.println("i: " + i + " - " + usersList.get(i).permission);
            System.out.println("i: " + i + " - " + usersList.get(i).age);
            System.out.println("i: " + i + " - " + usersList.get(i).phoneNumber);
        }
        int countRecords = usersList.size();
        System.out.println("There are " + countRecords + " users in the database");
        System.out.println();
    }

    public static void printArrayListAnimals(ArrayList<Animals> animalsList) {
        System.out.println();
        System.out.println("Listing all loaded animal records");
        for(int i = 0; i<animalsList.size(); i++) {
            System.out.println("id - " + animalsList.get(i).id);
            System.out.println("species - " + animalsList.get(i).species);
            System.out.println("category - " + animalsList.get(i).category);
            System.out.println("name - " + animalsList.get(i).name);
            System.out.println("age - " + animalsList.get(i).age);
            System.out.println("health - " + animalsList.get(i).health);
            System.out.println("weight - " + animalsList.get(i).weight);
            System.out.println("sex - " + animalsList.get(i).sex);
            System.out.println("notes - " + animalsList.get(i).notes);
            System.out.println("********************************************************");
        }
        int countRecords = animalsList.size();
        System.out.println("There are " + countRecords + " records in the database");
        System.out.println();
    }

    //Checks if username for a new user can be used, checks for duplicates
    public static boolean isUsernameValid(String newName, ArrayList<Users> usersList){
        boolean result = false;
        if(newName.length()>3) {
            for (int i = 0; i < usersList.size(); i++) {
                String nameFromList = usersList.get(i).name;
                if (nameFromList.equals(newName)) {
                    result = false;
                    break;
                } else {
                    result = true;
                }
            }
        }
        return result;
    }

    public static void checkFirstLaunch(String usersConfPath, String animalsDBPath){

        String path="./data";
        File file = new File(path);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null,"First launch detected. Please log in with administrator account. Username: admin, Password: password");
            //System.out.println("No Folder");
            file.mkdir();
            //System.out.println("Folder created");
        }

        File userFile = new File(usersConfPath);
        if((userFile.exists() == false) || (userFile.length() == 0)){
            createFirstUser(usersConfPath);
        }

        File animalFile = new File(animalsDBPath);
        if((animalFile.exists() == false) || (animalFile.length() == 0)){
            System.out.println("DEBUG WARNING - animalFile doesn't exist or is empty");
            createFirstAnimal(animalsDBPath);
        }
    }

    //Creating first admin account on first launch
    public static void createFirstUser(String usersConfPath){
        File userFile = new File(usersConfPath);

        String defaultFirstName = "admin";
        String defaultFirstPassword = "password";
        String defaultFirstPermission = "administrator";
        defaultFirstPassword = Functions.getHash(defaultFirstPassword, "MD5");
        //Users user = new Users(1,"admin",defaultFirstPassword, "admin",null, null);
        String toAppend = 1 + ", " + defaultFirstName  +  ", " + defaultFirstPassword +  ", " + defaultFirstPermission + ", " + " " + ", " + " ";
        appendStringToTextFile(usersConfPath,toAppend);
    }

    //Tables have to be populated with *something*, so this is a dummy row
    public static void createFirstAnimal(String animalsDBPath){
        File userFile = new File(animalsDBPath);
        String toAppend = "1, , , , , , , , , " ;
        appendStringToTextFile(animalsDBPath,toAppend);
    }

}
