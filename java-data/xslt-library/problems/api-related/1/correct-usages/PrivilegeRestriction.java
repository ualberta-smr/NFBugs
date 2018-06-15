package com.offerready.xslt.config;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
public class PrivilegeRestriction implements Serializable {
  
  public static @Nonnull <P extends HasPrivilegeRestriction> List<P> restrict(@Nonnull Collection<P> input, @Nonnull Privilege privilege) {
        List<P> result = new ArrayList<P>(input.size());
        for (P candidate : input)
            if (candidate.getPrivilegeRestriction().isAllowed(privilege))
                result.add(candidate);
        return result;
    }
    
  }
