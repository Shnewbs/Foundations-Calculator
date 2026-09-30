package com.foundations.calculator.core;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import com.foundations.guide.api.*;
import static org.junit.jupiter.api.Assertions.*;
/** Native Gradle test source set; links the real shared JAR, not duplicate API classes. */
public final class GuideApiTest {
 @Test void strictParserAndBoundedNavigation(){GuideContractAssertions.core();GuideContractAssertions.snapshots();}
 @Test void authoredGuideParsesAndAllLinksResolve() throws Exception {assertEquals(1,GuideContractAssertions.corpus(Path.of("src/main/resources/assets")).books().size());}
 @Test void independentPublisherAndMasterConsumer() throws Exception {GuideContractAssertions.negotiation(GuideContractAssertions.corpus(Path.of("src/main/resources/assets")));}
 @Test void sharedLibraryIsNotShadowCopiedIntoMod(){assertEquals("com.foundations:foundations-guide-api:1.0.0",GuideApi.ARTIFACT);assertFalse(java.nio.file.Files.exists(Path.of("build/classes/java/main/com/foundations/guide/api/GuideApi.class")));}
}
