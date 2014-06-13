package com.captainbern.minecraft.error;

public class Report {

    private final ReportDescription description;
    private final Throwable error;
    private final Object[] params;
    protected Report(ReportBuilder reportBuilder) {
        this.description = reportBuilder.description;
        this.error = reportBuilder.error;
        this.params = reportBuilder.messageParams;
    }

    public static ReportBuilder newBuilder(ReportDescription description) {
        return new ReportBuilder().withDescription(description);
    }

    public ReportDescription getReportDescription() {
        return this.description;
    }

    public Throwable getError() {
        return this.error;
    }

    public Object[] getMessageParameters() {
        return this.params;
    }

    public static class ReportBuilder {

        private ReportDescription description;
        private Throwable error;
        private Object[] messageParams;

        private ReportBuilder() {
        }

        public ReportBuilder withDescription(ReportDescription description) {
            this.description = description;
            return this;
        }

        public ReportBuilder withError(final Throwable throwable) {
            this.error = throwable;
            return this;
        }

        public ReportBuilder withMessageParams(Object[] params) {
            this.messageParams = params;
            return this;
        }

        public Report build() {
            return new Report(this);
        }
    }
}
