package fr.guronzan.mediatheque.mappingclasses.domain;

/**
 * AbstractPersistentObject
 */
public abstract class AbstractPersistentObject {
    private String id = IdGenerator.createId();

    private Integer version = null;

    public AbstractPersistentObject() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String value) {
        this.id = value;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(final Integer value) {
        this.version = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractPersistentObject)) {
            return false;
        }

        final AbstractPersistentObject other = (AbstractPersistentObject) o;

        // if the id is missing, return false
        if (this.id == null) {
            return false;
        }

        // equivalence by id
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + this.id + "]";
    }

    public boolean isCreation() {
        return this.version == null;
    }

    public void regenerateId() {
        this.id = IdGenerator.createId();
        this.version = null;
    }
}