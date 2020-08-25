package by.buyinghouses.service;

import by.buyinghouses.domain.User;
import org.springframework.stereotype.Service;

//do not forget to redo

@Service
public class MessageCreatorService {

    public String createEmailMessage(User user) {
        return String.format(
                "Hello, %s! "
                        + "\n"
                        + "Welcome to Buyinghouses. Please, visit our link: http://localhost:8080/activate/%s",
                user.getUserName(),
                user.getActivationCode()
        );
    }

    public String createEmptyRepeatedPasswordMessage() {
        return "Please fill field repeated password";
    }

    public String createDifferentPasswordsMessage() {
        return "Passwords are different";
    }

    public String createUserExistMessage() {
        return "User with the same email/login already exist";
    }

    public String createSuccessfullyActivatedMessage() {
        return "User successfully activated";
    }

    public String createActivationCodeNotFoundMessage() {
        return "Activation code is not found";
    }

    public String createAccommodationExistMessage() {
        return "Accommodation with the same name already exists";
    }
}