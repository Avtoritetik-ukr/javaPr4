module order.processing {
    requires static lombok;

    exports com.example.order.model;
    exports com.example.order.processor;
    exports com.example.order.exception;
}