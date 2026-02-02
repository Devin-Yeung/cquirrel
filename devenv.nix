{
  pkgs,
  lib,
  config,
  inputs,
  ...
}:

{
  # https://devenv.sh/packages/
  packages = with pkgs; [
    git
  ];

  # https://devenv.sh/languages/
  languages = {
    java = {
      enable = true;
      jdk.package = pkgs.jdk21;
      maven.enable = true;
    };
    nix = {
      enable = true;
    };
  };

  # https://devenv.sh/basics/
  enterShell = ''
    git --version # Use packages
  '';

  # https://devenv.sh/tests/
  enterTest = ''
    git --version | grep --color=auto "${pkgs.git.version}"
  '';

  # https://devenv.sh/git-hooks/
  git-hooks = {
    hooks = {
      nixfmt.enable = true;
      # tidy up Maven POM files before committing
      mvn-tidy = {
        enable = true;
        name = "Tidy Maven POM files";
        entry = "mvn tidy:pom";
        files = "pom\\.xml$";
        pass_filenames = false;
      };
    };
    package = pkgs.prek;
  };

  # See full reference at https://devenv.sh/reference/options/
}
