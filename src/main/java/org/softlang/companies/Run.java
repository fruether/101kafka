package org.softlang.companies;

import org.softlang.companies.Consumer101;
import org.softlang.companies.Producer101;

import java.io.IOException;

/**
 * Pick whether we want to run as producer or consumer. This lets us
 * have a single executable as a build target.
 */
public class Run {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Must have either 'company' or 'total' as argument");
        }
        switch (args[0]) {
            case "company":
                Producer101.main(args);
                break;
            case "total":
                Consumer101.main(args);
                break;
            default:
                throw new IllegalArgumentException("Don't know how to do " + args[0]);
        }
    }
}
