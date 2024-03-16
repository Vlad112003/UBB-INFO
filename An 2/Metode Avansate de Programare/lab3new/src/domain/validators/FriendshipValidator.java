package domain.validators;

import domain.FriendShip;

public class FriendshipValidator implements Validator<FriendShip>{

    /**
     * FriendShip's user must have different ids
     */
    @Override
    public void validate(FriendShip entity) throws ValidationException{
        if(entity.getUser1().getId() == entity.getUser2().getId())
            throw new ValidationException("FriendShip's user must have different ids");
    }
}
