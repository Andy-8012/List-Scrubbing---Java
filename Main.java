import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class

public class Main {
    public static void main(String[] args) {
        //Get the user input on what they want the new lists to be
        System.out.println("Thank you for using the Home Finder program.");
        System.out.println("You will be prompted to create two lists. One will hold the appartments in your list and the other will contain the rest of the homes.");
        Scanner inputScanner;
        inputScanner = new Scanner(System.in);
        System.out.println("Please enter the file name for the appartment adresses: ");
        String appartmentList;
        appartmentList = inputScanner.nextLine();
        System.out.println("Please enter the file name for the home addresses: ");
        String homeList;
        homeList = inputScanner.nextLine();

        inputScanner.close();
        
        //create the two lists and run the removeAppartments() function.
        createFile(appartmentList);
        createFile(homeList);
        removeAppartments("sampleList.csv", appartmentList, homeList);

    }

    
    //Method for removing the Appartments from a list of homes
    static void removeAppartments(String file, String appartmentList, String homeList){
        try {
            //creating file readers and writers to read and write to new files
            File myFile;
            myFile = new File(file);
            Scanner myReader;
            myReader = new Scanner(myFile);
            try{
                FileWriter myWriter;
                myWriter = new FileWriter(appartmentList);
                FileWriter myWriter2;
                myWriter2 = new FileWriter(homeList);

                //While reading the file we will look for any address that
                //has "apt" in it and add it to the appartment file
                //If it does not have "apt" it will be added to the homes file
                while (myReader.hasNextLine()){
                    String data;
                    data = myReader.nextLine();
                    //Seperate the data into an array of words to only look at the adress
                    //no need to search the other fields of the file.
                    String[] dataStrings;
                    dataStrings = data.split(",");
                    //the index of your adress may need to be switched from 6
                    if (isApartment(dataStrings[6])){
                        myWriter.write(data + "\n");
                    }
                    else{
                        myWriter2.write(data + "\n");
                    }
                }

                //Close the readers and writers so they don't stay open
                myWriter.close();
                myWriter2.close();
                myReader.close();

            //Finish by catching any execptions if an error occurs
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            } 
        }
        catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Creates a basic text file 
    static void createFile(String fileName){
        try {
            File myNewFile;
            myNewFile = new File(fileName);
            if (myNewFile.createNewFile()){
                System.out.println("File created: " + myNewFile.getName());
            }
            else {
                System.out.println("File already exisits");
            }
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Seeing if "apt" occurs in the address
    static boolean isApartment(String address){
        String lowerAddress;
        lowerAddress = address.toLowerCase();
        if (lowerAddress.indexOf(" apt") >= 0){
            return true;
        }
        else if (lowerAddress.indexOf("#") >= 0){
            return true;
        }
        else if (lowerAddress.indexOf(" unit ") >= 0){
            return true;
        }
        else {
            return false;
        }
    }

    //Writing a file
    static void writeFile(String data){
        try {
            FileWriter myWriter = new FileWriter("newFile.txt");
            myWriter.append(data);
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}