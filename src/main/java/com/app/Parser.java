package com.app;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.List;

public class Parser {
    @Option(name = "-p", usage="prefix for output files")
    private String p;
    @Option(name="-a", usage="add to exist files")
    private boolean a;
    @Option(name="-o", usage="path to output files")
    private String o;
    @Option(name="-s", usage="short statistics", forbids = {"-f"})
    private boolean s;
    @Option(name="-f", usage="full statistics", forbids = {"-s"})
    private boolean f;
    @Argument
    private List<String> files;

    //TODO: Autowired Filter

    public static void main(String[] args) {
        new Parser().run(args);
    }

    private void run(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (files == null || files.isEmpty()) {
                System.err.println("Input error! Files are missing!");
                return;
            }
            if (p.isEmpty()) {
                System.err.println("Input error! Prefix are missing!");
            }
            if (o == null) {
                o = "";
            }

            Filter filter = new Filter(files, p, a, o, s, f);
            filter.filter();

        } catch (IOException | CmdLineException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}