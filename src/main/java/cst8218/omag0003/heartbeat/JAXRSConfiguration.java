package cst8218.omag0003.heartbeat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@BasicAuthenticationMechanismDefinition
@DatabaseIdentityStoreDefinition(
   dataSourceLookup = "${'java:app/MariaDBas1'}",
   callerQuery = "#{'select password from appuser where userid = ?'}",
   groupsQuery = "select groupname from appuser where userid = ?",
   hashAlgorithm = PasswordHash.class,
   priority = 10
)
@Named
@ApplicationScoped

@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
    
}
