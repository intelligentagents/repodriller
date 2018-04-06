package org.repodriller.experiment.q3;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;
import org.repodriller.util.Constant;

import java.util.Map;

// Q3: What is the average of modifications per file?
public class Q3Study implements Study {

    public static void main(String[] args) {
        new RepoDriller().start(new Q3Study());
    }

    @Override
    public void execute() {

        ModificationsPerFileVisitor visitor = new ModificationsPerFileVisitor();

        new RepositoryMining()
                .in(GitRepository.singleProject(Constant.REPO_PATH))
                .through(Commits.all())
                .process(visitor)
                .withThreads(3)
                .mine();

        CSVFile csv = new CSVFile(Constant.OUTPUT_PATH + "q3.csv");
        for(Map.Entry<String, Integer> k : visitor.getFiles().entrySet()) {
            csv.write(
                    k.getKey(),
                    k.getValue()
            );
        }
    }
}
