package demo.server;
import demo.entity.Contact;
import demo.entity.ContactBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.*;

@Path("api/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class Api {
    protected static final List<Contact> contacts;

    static {
        contacts = new ArrayList<>();
        contacts.add(new Contact(new ContactBuilder().withID(1).withName("Dmytro").withPhone("0568772345")));
        contacts.add(new Contact(new ContactBuilder().withID(2).withName("Anna").withPhone("0568735325")));
        contacts.add(new Contact(new ContactBuilder().withID(3).withName("Sergey").withPhone("05611112345")));
        contacts.add(new Contact(new ContactBuilder().withID(4).withName("Alina").withPhone("05687782")));
        contacts.add(new Contact(new ContactBuilder().withID(5).withName("Danil").withPhone("05798574212")));
        contacts.add(new Contact(new ContactBuilder().withID(6).withName("Slim").withPhone("09090909332")));
    }

    @GET
    public List<Contact> getContacts(){return contacts;}

    @GET
    @Path("find")
    public Contact getContact(@QueryParam("id") int id){
        Contact contact = new ContactBuilder().withID(id).build();

        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

        if(index >= 0)
            return contacts.get(index);
        else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createContact(@QueryParam("command") String command, Contact contact) {

        if (Objects.isNull(contact.getId())) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

       if (command.equals("createContact")) {
               if (index < 0) {
                contacts.add(contact);
                return Response
                        .status(Response.Status.CREATED)
                        .location(URI.create("/api/contact"))
                        .entity("{\"message\" : \"success\"}")
                        .build();
            } else {
                throw new WebApplicationException(Response.Status.CONFLICT);
            }
        } else {
            return Response
                    .status(400)
                    .entity("{\"message\" : \"error\"}")
                    .build();
        }
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteContact(@PathParam("id") Integer id){
        Contact contact = new ContactBuilder().withID(id).build();

        int index = Collections.binarySearch(contacts,contact,Comparator.comparing(Contact::getId));

        if(index >= 0){
            contacts.remove(index);
            return Response
                    .status(Response.Status.OK)
                    .entity("{\"message\" : \"success\"}")
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\" : \"error\"}")
                    .build();
        }
    }

    @PATCH
    @Path("{id: [0-9]+}")
    public Response updateContactEmail(@PathParam("id") Integer id, @QueryParam("phone") String phone){

        Contact contact = new ContactBuilder().withID(id).build();

        int index = Collections.binarySearch(contacts,contact,Comparator.comparing(Contact::getId));


        if(index >= 0){
            Contact updatedContact = contacts.get(index);
            updatedContact.setPhone(phone);
            contacts.set(index, updatedContact);
            return Response
                    .status(Response.Status.OK)
                    .entity("{\"message\" : \"success\"}")
                    .build();
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
