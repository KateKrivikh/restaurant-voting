package ru.voting;

import javax.validation.groups.Default;

public class View {
    // Validate only when DB save/update
    public interface Persist extends Default {}

    // Validate only form REST
    public interface Rest extends Default {}
}
