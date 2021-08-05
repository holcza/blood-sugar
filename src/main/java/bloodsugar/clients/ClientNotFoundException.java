package bloodsugar.clients;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class ClientNotFoundException extends AbstractThrowableProblem {

    public ClientNotFoundException(long id) {
        super(
                URI.create("clients/client-not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Client with id '%d' not found", id)
        );
    }
}
