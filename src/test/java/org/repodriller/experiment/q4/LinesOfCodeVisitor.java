package org.repodriller.experiment.q4;

import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.RepositoryFile;
import org.repodriller.scm.SCMRepository;
import org.repodriller.util.RDFileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class LinesOfCodeVisitor implements CommitVisitor {
    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
        try {
            repo.getScm().checkout(commit.getHash());
            List<RepositoryFile> files = repo.getScm().files();

            int totalLoc = 0;

            for (RepositoryFile file : files) {
                if (!file.fileNameEndsWith("java")) {
                    continue;
                }

                File soFile = file.getFile();
                int loc = RDFileUtils.countLineNumbers(RDFileUtils.readFile(soFile));
                totalLoc += loc;
            }

            writer.write(
                    new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(commit.getDate().getTime()),
                    commit.getHash(),
                    totalLoc
            );
        } finally {
            repo.getScm().reset();
        }
    }

    @Override
    public String name() {
        return "loc-per-commit";
    }
}
