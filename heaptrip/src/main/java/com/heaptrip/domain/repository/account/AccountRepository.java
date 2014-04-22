package com.heaptrip.domain.repository.account;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account> {

    Account findById(String accountId);

    void changeStatus(String accountId, AccountStatusEnum accountStatus);

    void changeEmail(String accountId, String newEmail);

    void saveSetting(String accountId, Setting setting);

    void saveProfile(String accountId, Profile profile);

    List<Account> findUsersByEmail(String email, AccountStatusEnum accountStatus);

    public AccountRating getRating(String accountId);

    public void setRating(String accountId, AccountRating accountRating);

    public void updateRating(String accountId, double ratingValue);

    public Image getImage(String accountId);

    public void changeImage(String accountId, Image image);

    public String getName(String accountId);
}
