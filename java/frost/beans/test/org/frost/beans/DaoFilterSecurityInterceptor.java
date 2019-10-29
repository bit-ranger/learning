package org.frost.beans;

/**
 * Created by sllx on 14-12-8.
 */
public class DaoFilterSecurityInterceptor {

    private DaoAccessDecisionManager accessDecisionManager;
    private DaoInvocationSecurityMetadataSource securityMetadataSource;

    public void setAccessDecisionManager(DaoAccessDecisionManager accessDecisionManager) {
        this.accessDecisionManager = accessDecisionManager;
    }

    public void setSecurityMetadataSource(DaoInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }
}
