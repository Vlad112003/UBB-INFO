package ro.ubbcluj.cs.map.lab82nd.domain.validators;
import ro.ubbcluj.cs.map.lab82nd.domain.User;

public class UserValidator implements Validator<User>{

    @Override
    public void validate(User entity) throws ValidationException{
        validateFirstName(entity.getFirstName());
        validateLastName(entity.getLastName());
        validateEmail(entity.getEmail());
    }


    /**
     * It must not be null
     * the first name must be less than 100 characters
     * it mustn't be empty
     * it's first character must be a letter
     */
    private void validateFirstName(String firstName) throws ValidationException{
        if(firstName == null)
            throw new ValidationException("First name must not be null!");
        else if (firstName.length() >=100)
            throw new ValidationException("First name is too long!");
        else if (firstName.isEmpty())
            throw new ValidationException("First name must not be empty!");
        else if (!Character.isAlphabetic(firstName.charAt(0)))
            throw new ValidationException("First name must start with a letter!");

    }


    /**
     * It must not be null
     * the first name must be less than 100 characters
     * it mustn't be empty
     * it's first character must be a letter
     */
    private void validateLastName(String lastName) throws ValidationException{
        if(lastName == null)
            throw new ValidationException("Last name must not be null!");
        else if (lastName.length() >=100)
            throw new ValidationException("Last name is too long!");
        else if (lastName.isEmpty())
            throw new ValidationException("Last name must not be empty!");
        else if (!Character.isAlphabetic(lastName.charAt(0)))
            throw new ValidationException("Last name must start with a letter!");
    }

    /**
     * It must not be null
     * the first name must be less than 100 characters
     * it mustn't be empty
     * it must contain one and only one @ character
     * --- The email must also be unique, for that we will check in the service
     */
    private void validateEmail(String email) throws ValidationException{
        if(email == null)
            throw new ValidationException("Email must not be null!");
        else if (email.length() >=100)
            throw new ValidationException("Email is too long!");
        else if (email.isEmpty())
            throw new ValidationException("Email must not be empty!");
        else if (!email.contains("@"))
            throw new ValidationException("Email must contain @!");
        else if (email.indexOf('@') != email.lastIndexOf('@'))
            throw new ValidationException("Email must contain only one @!");
    }
}
