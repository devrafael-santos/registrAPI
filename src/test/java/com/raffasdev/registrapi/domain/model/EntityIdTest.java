package com.raffasdev.registrapi.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityIdTest {

    @Test
    @DisplayName("newId creates a new EntityId with a valid UUID")
    void newId_CreatesNewEntityIdWithValidUUID() {
        EntityId entityId = EntityId.newId();

        assertNotNull(entityId);
        assertNotNull(entityId.getValue());
        assertFalse(entityId.getValue().toString().isEmpty());
    }

    @Test
    @DisplayName("of creates EntityId with provided UUID")
    void of_CreatesEntityIdWithProvidedUUID() {
        var expectedUuid = java.util.UUID.randomUUID();
        EntityId entityId = EntityId.of(expectedUuid);

        assertNotNull(entityId);
        assertEquals(expectedUuid, entityId.getValue());
    }

    @Test
    @DisplayName("of throws IllegalArgumentException when null UUID is provided")
    void of_ThrowsIllegalArgumentException_WhenNullUUIDIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> EntityId.of(null));
    }

    @Test
    @DisplayName("equals returns true when EntityIds are equal")
    void equals_ReturnsTrue_WhenEntityIdsAreEqual() {
        EntityId id1 = EntityId.newId();
        EntityId id2 = EntityId.of(id1.getValue());

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("equals returns false when EntityIds are not equal")
    void equals_ReturnsFalse_WhenEntityIdsAreNotEqual() {
        EntityId id1 = EntityId.newId();
        EntityId id2 = EntityId.newId();

        assertNotEquals(id1, id2);
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("toString returns the UUID as a string")
    void toString_ReturnsUUIDAsString() {
        EntityId entityId = EntityId.newId();
        String expectedString = entityId.getValue().toString();

        assertEquals(expectedString, entityId.toString());
    }

    @Test
    @DisplayName("getValue returns the UUID value")
    void getValue_ReturnsUUIDValue() {
        EntityId entityId = EntityId.newId();
        java.util.UUID expectedUuid = entityId.getValue();

        assertNotNull(expectedUuid);
        assertEquals(expectedUuid, entityId.getValue());
    }
}