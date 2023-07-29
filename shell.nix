{ pkgs ? import <nixpkgs> {} }:

with pkgs;

mkShell {
  buildInputs = [
    clojure

    openjdk17
    nodejs
    maven

    babashka
    clj-kondo
    clojure-lsp
    jet

    awscli2
  ];
}
