package utils.javautils;

public class GenerateTestData {
    //Generate Random Test Data
    public static int randomNumber = (int) (Math.random() * 10000); // Generate a random number between 0 and 999
    public static String TestUserName = generateRandomUsername();
    public static String TestEmail = generateRandomEmail();
    public static String TestPassword=generateRandomPassword();
    public static String TestMobile=generateRandomMobile();


    //Generate Test User:
    public static String generateRandomUsername() {

        return "User" + "_" + randomNumber;
    }

    //Generate Password:
    public static String generateRandomPassword() {

        return "User" + "_" + randomNumber;
    }

    //Generate Test Email:
    public static String generateRandomEmail() {
        return "varun.bhosale" + "+" + randomNumber + "@tekditechnologies.com";
    }

    //Generate Mobile:
    public static String generateRandomMobile() {
        int rNumber = (int) (Math.random() * 100);
        return "99600514" + rNumber;

    }


    public static long generatePhone() {
        long min = 7000000000L;
        long max = 9999999999L;
        return (long) (Math.random() * (max - min + 1)) + min;
    }
    public static String generateAddress() {
        String[] campaignTitles = {
                "Pune",
                "Mumbai",
                "Nashik",
        };
        int randomIndex = (int) (Math.random() * campaignTitles.length);
        return campaignTitles[randomIndex];
    }


}
