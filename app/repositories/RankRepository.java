package repositories;

public class RankRepository {

    private final DatabaseExecutionContext executionContext;

    public RankRepository(DatabaseExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

}