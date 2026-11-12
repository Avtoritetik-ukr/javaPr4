module order.threading {
    requires transitive order.processing;
    requires order.storage;

    exports com.example.order.threading;
}