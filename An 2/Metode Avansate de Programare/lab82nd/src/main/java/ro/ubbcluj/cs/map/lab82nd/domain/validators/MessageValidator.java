package ro.ubbcluj.cs.map.lab82nd.domain.validators;

import ro.ubbcluj.cs.map.lab82nd.domain.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageValidator implements Validator<Message>{
    @Override
    public void validate(Message entity) throws ValidationException {
        validateFrom(entity.getFrom());
        validateTo(entity.getTo());
        validateMessage(entity.getMessage());
        validateDate(entity.getDate());
    }

    private void validateFrom(UUID from) throws ValidationException{
        String fromStr = from.toString();
        if(fromStr == null)
            throw new ValidationException("From must not be null!");
        else if (fromStr.length() >=100)
            throw new ValidationException("From is too long!");
        else if (fromStr.isEmpty())
            throw new ValidationException("From must not be empty!");
    }

    private void validateTo(UUID to) throws ValidationException{
        String toStr = to.toString();
        if(toStr == null)
            throw new ValidationException("To must not be null!");
        else if (toStr.length() >=100)
            throw new ValidationException("To is too long!");
        else if (toStr.isEmpty())
            throw new ValidationException("To must not be empty!");
    }

    private void validateMessage(String message) throws ValidationException{
        if(message == null)
            throw new ValidationException("Message must not be null!");
        else if (message.length() >=1000)
            throw new ValidationException("Message is too long!");
        else if (message.isEmpty())
            throw new ValidationException("Message must not be empty!");
    }

    private void validateDate(LocalDateTime date) throws ValidationException{
        String dateStr = date.toString();
        if(dateStr == null)
            throw new ValidationException("Date must not be null!");
        else if (dateStr.length() >=100)
            throw new ValidationException("Date is too long!");
        else if (dateStr.isEmpty())
            throw new ValidationException("Date must not be empty!");
    }
}
