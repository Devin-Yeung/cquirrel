{
  pkgs,
  lib,
  config,
  inputs,
  ...
}:

{
  # https://devenv.sh/packages/
  packages = [ pkgs.git ];

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
    };
    package = pkgs.prek;
  };

  # See full reference at https://devenv.sh/reference/options/
}
