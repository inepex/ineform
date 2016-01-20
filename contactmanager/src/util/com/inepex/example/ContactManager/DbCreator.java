package com.inepex.example.ContactManager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.User;

public class DbCreator extends EntityManagerInitializier {

    public static void main(String[] args) {
        EntityManager em = initInDropCreateMode();
        em.getTransaction().begin();

        // static stuffs
        User johnBlack = new User("John", "Black", "john.black@inepex.com", "pass123");
        em.persist(johnBlack);

        User barbaraGreen = new User("Barbara", "Green", "barbara.green@inepex.com", "pass123");
        em.persist(barbaraGreen);

        User adamWhite = new User("Adam", "White", "adam.white@inepex.com", "pass123");
        em.persist(adamWhite);

        PhoneNumberType home = new PhoneNumberType("Home");
        em.persist(home);

        PhoneNumberType mobile = new PhoneNumberType("Mobile");
        em.persist(mobile);

        PhoneNumberType work = new PhoneNumberType("Work");
        em.persist(work);

        PhoneNumberType other = new PhoneNumberType("Other");
        em.persist(other);

        // editable stuffs
        Company blueEagle = new Company(
            "Blue Eagle Corporation",
            "+22-883-2678",
            "info@blueeagle.com",
            "http://www.blueeagle.com",
            list(
                new Contact(
                    "Boss Blue",
                    list(
                        new PhoneNumber("+22-223-5839", home),
                        new PhoneNumber("+22-545-3759", work),
                        new PhoneNumber("+22-329-9524", other)),
                    list(
                        new EmailAddress("boss@blueeagle.com"),
                        new EmailAddress("charlie74@gmail.com"))),
                new Contact(
                    "John Blue",
                    list(
                        new PhoneNumber("+22-133-2356", home),
                        new PhoneNumber("+23-3854-9554", work),
                        new PhoneNumber("+22-2345-4385", other)),
                    list(
                        new EmailAddress("john.blue@blueeagle.com"),
                        new EmailAddress("jack.the.ripper@hotmail.com")))));

        linkCompany(blueEagle);
        em.persist(blueEagle);

        Company redYellow = new Company(
            "Red-Yellow Ltd.",
            "+10-239-2345",
            "contact@redyellow.com",
            "http://www.red-yellow.com",
            list(
                new Contact(
                    "Ryan",
                    list(
                        new PhoneNumber("+10-123-1123", home),
                        new PhoneNumber("+10-942-2245", work),
                        new PhoneNumber("+10-100-1943", other)),
                    list(
                        new EmailAddress("ryan@redyellow.com"),
                        new EmailAddress("ryan.lapas@redyellow.com"))),
                new Contact(
                    "Jeremy",
                    list(new PhoneNumber("+10-123-222", home)),
                    list(
                        new EmailAddress("jeremy@redyellow.com"),
                        new EmailAddress("jeremy85@gmail.com"))),
                new Contact(
                    "Mr Charlie Brown",
                    list(
                        new PhoneNumber("+10-153-2522", home),
                        new PhoneNumber("+10-323-25223", work),
                        new PhoneNumber("+10-126-2473", other)),
                    null)));

        linkCompany(redYellow);
        em.persist(redYellow);

        Company inclust = new Company(
            "Inclust Systems Kft.",
            "+3630-638-3169",
            "support@inclust.com",
            "http://inclust.com/",
            list(
                new Contact(
                    "Mihály Csordás",
                    null,
                    list(new EmailAddress("mihaly.csordas@inclust.com "))),
                new Contact(
                    "Szabolcs Csincsi",
                    null,
                    list(new EmailAddress("szabolcs.csincsi@inclust.com"))),
                new Contact("Gabor Madi", null, list(new EmailAddress("gabor.madi@inclust.com")))));

        linkCompany(inclust);
        em.persist(inclust);

        Company bme = new Company(
            "Budapest University of Technology and Economics",
            "+1-463-1111",
            "info@mail.bme.hu",
            "http://portal.bme.hu/",
            list(
                new Contact(
                    "Gábor Nagy",
                    list(
                        new PhoneNumber("+1-153-2522", home),
                        new PhoneNumber("+1-126-2473", other)),
                    list(new EmailAddress("nagygabor@mail.bme.hu"))),
                new Contact(
                    "Borbála Lantos",
                    list(
                        new PhoneNumber("+1-153-2522", home),
                        new PhoneNumber("+1-323-25223", work),
                        new PhoneNumber("+1-126-2473", other)),
                    list(
                        new EmailAddress("borbala.lantos@mail.bme.hu"),
                        new EmailAddress("titkarsag.ppke@mail.bme.hu")))));

        linkCompany(bme);
        em.persist(bme);

        Company friedCheeseCorp = new Company(
            "Fried Cheese Meal",
            "+34-063-1811",
            "mail@sfc.hu",
            "http://sfc.hu/",
            list(
                new Contact(
                    "Gábor Nagymarosi",
                    list(
                        new PhoneNumber("+34-153-2522", home),
                        new PhoneNumber("+34-126-2473", other)),
                    list(new EmailAddress("nagymarosigabor@sfc.hu"))),
                new Contact(
                    "Andrea Tóth",
                    list(
                        new PhoneNumber("+34-153-2522", home),
                        new PhoneNumber("+34-323-25223", work)),
                    list(new EmailAddress("tothandrea@sfc.hu"))),
                new Contact(
                    "Borbála Lakatos",
                    list(
                        new PhoneNumber("+34-153-2522", home),
                        new PhoneNumber("+34-323-25223", work),
                        new PhoneNumber("+34-126-2473", other)),
                    list(
                        new EmailAddress("borbala.lakatos@sfc.hu"),
                        new EmailAddress("titkarsag@sfc.hu")))));

        linkCompany(friedCheeseCorp);
        em.persist(friedCheeseCorp);

        Company CAndC = new Company(
            "Cow and Chichen Ltd",
            "+9-063-1811",
            "mail@candc.com",
            "http://candc.co.uk/",
            list(new Contact("Chicken", list(new PhoneNumber("+9-153-2522", work)), null)));

        linkCompany(CAndC);
        em.persist(CAndC);

        Company dexter = new Company(
            "Dexter Labs Inc.",
            "+2-142-234",
            "dexter@dexterlabs.com",
            "http://dexterlabs.com/",
            list(
                new Contact(
                    "Dexter",
                    list(new PhoneNumber("+34-153-2522", work)),
                    list(new EmailAddress("dexter@dexterlabs.com")))));

        linkCompany(dexter);
        em.persist(dexter);

        Company eee = new Company(
            "Ed, Edd n Eddy Toys",
            "+43-142-234",
            "info@eeetoys.com",
            "http://eeetoys.com/",
            list(
                new Contact(
                    "Ed",
                    list(new PhoneNumber("+43-153-2522", work)),
                    list(new EmailAddress("ed@eeetoys.com"))),
                new Contact(
                    "Edd",
                    list(new PhoneNumber("+43-153-2524", work)),
                    list(new EmailAddress("edd@eeetoys.com"))),
                new Contact(
                    "Edddy",
                    list(new PhoneNumber("+43-153-2556", work)),
                    list(new EmailAddress("eddy@eeetoys.com")))));

        linkCompany(eee);
        em.persist(eee);

        Company gRex = new Company(
            "Generator Rex Corp.",
            "+1-123-2423",
            "generatorrex@generatorrex.com",
            "http://generatorrex.com/",
            list(
                new Contact("John", list(new PhoneNumber("+1-153-2522", work)), null),
                new Contact("Edward", list(new PhoneNumber("+1-153-2524", work)), null),
                new Contact("Roy", list(new PhoneNumber("+1-153-2556", work)), null)));

        linkCompany(gRex);
        em.persist(gRex);

        em.getTransaction().commit();
    }

    private static void linkCompany(Company company) {
        for (Contact c : company.getContacts()) {
            c.setCompany(company);
        }
    }

    public static <E> List<E> list(E... elements) {
        ArrayList<E> retList = new ArrayList<E>();
        if (elements != null) {
            for (E e : elements)
                retList.add(e);
        }
        return retList;
    }

}
