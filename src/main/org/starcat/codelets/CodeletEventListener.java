package org.starcat.codelets;

import java.util.EventListener;

/**
 * This is an interface for things that listen to Starcat components for 
 * CodeletEvents. 
 * While this means the interface for listening to events is the same 
 * for all components, it is still the case that components register 
 * themselves as listeners to specific components. 
 */
public interface CodeletEventListener extends EventListener 
{
    void handleCodeletEvent(CodeletEvent event);
}
