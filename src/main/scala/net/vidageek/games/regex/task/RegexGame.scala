package net.vidageek.games.regex.task

import net.vidageek.games.regex.task.MatcherTargets.from
import net.vidageek.games.regex.Descriptions
import net.vidageek.games.task.Match
import net.vidageek.games.Game

class RegexGame(descriptions : Descriptions) extends Game {
    private val tasks = new Tasks()
    addCharsExercises(descriptions)
    addCharClassesExercises(descriptions)
    addOpositeCharClassExercises(descriptions)
    addPipeOperator(descriptions)
    addOperatorsExercises(descriptions)
    addCaptureGroupExercises(descriptions)
    addAnchoringExercises(descriptions)
    addModesExercises(descriptions)
    addRealWorldRegexes(descriptions);

    private def addCharsExercises(descriptions : Descriptions) {
        val group = new TaskGroup("Caracteres Simples", "match.chars", descriptions)
        group.add(new Match(from("a")))
        group.add(new Match(from("abc")))
        group.add(new Match(from("\\")))
        group.add(new Match(from("$")))
        group.add(new Match(from("abcdefg12345")))
        group.add(new Match(from("AbCdEfG6")))
        group.add(new Match(from("ab$cd^Ef\\G1")))
        group.add(new Match(from("")))
        group.add(new Match(from(" ")))
        group.add(new Match(from("\n")))
        group.add(new Match(from("\t")))
        tasks.add(group)
    }

    private def addCharClassesExercises(descriptions : Descriptions) {
        val group = new TaskGroup("Classes de Caracteres", "match.chars.classes", descriptions)
        group.add(new Match(from("a", "b")))
        group.add(new Match(from("ad", "bd")))
        group.add(new Match(from("a", "b", "c", "A", "B", "C", "D")))
        group.add(new Match(from("0", "1", "2")))
        group.add(new Match(from("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "x", "w", "y", "z")))
        group.add(new Match(from("1", "4", "5")))
        group.add(new Match(from("1a", "4a", "5a")))
        group.add(new Match(from("1", "4", "5", "a")))
        group.add(new Match(from(" ", "\t", "\n", "\f", "\r")))
        group.add(new Match(from(" a", "\ta", "\na")))
        group.add(new Match(from(" ", "\t", "\n", "a")))
        group.add(new Match(from("a", "b", "9")))
        group.add(new Match(from("ap", "bp", "9p")))
        group.add(new Match(from("a", "B", "9", "$", "\t", " ")))
        tasks.add(group)
    }

    private def addOpositeCharClassExercises(descriptions : Descriptions) {
        val group = new TaskGroup("Classes de Caracteres Opostas", "match.negate", descriptions)
        group.add(new NegateAndMatch(from("a", "b"), from("c", "d")))
        group.add(new NegateAndMatch(from("ad", "bd", "cd"), from("dd", "ed")))
        group.add(new NegateAndMatch(from("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"), from(" ", "a")))
        group.add(new NegateAndMatch(from("1a", "4a", "5a"), from(" a", "$a")))
        group.add(new NegateAndMatch(from("1", "4", "5", "a"), from(" ", "b")))
        group.add(new NegateAndMatch(from("\t", "\n", "\f", "\r"), from("A", "w")))
        group.add(new NegateAndMatch(from(" a", "\ta", "\na"), from("ca", "#a")))
        group.add(new NegateAndMatch(from(" ", "\t", "\n", "a"), from("Z", "A")))
        group.add(new NegateAndMatch(from("a", "B", "9"), from(" ", "$")))
        group.add(new NegateAndMatch(from("ap", "Bp", "9p"), from("$p", "#p")))
        tasks.add(group)
    }

    private def addPipeOperator(descriptions : Descriptions) {
        val group = new TaskGroup("M&uacute;ltiplos Padr&otilde;es", "match.pipe", descriptions)
        group.add(new NegateAndMatch(from("ab", "ba"), from("a", "b")))
        group.add(new NegateAndMatch(from("ba", "baa"), from("aa", "bb", "ab")))
        tasks.add(group)
    }

    private def addOperatorsExercises(descriptions : Descriptions) {
        val group = new TaskGroup("Operadores de Repeti&ccedil;&atilde;o", "match.operators", descriptions)
        group.add(new NegateAndMatch(from("b"), from("", "a")))
        group.add(new NegateAndMatch(from("aa"), from("", "a", "b")))
        group.add(new NegateAndMatch(from("", "aaaaaaaaab"), from("a", "aa", "aaaaaaaaa")));
        group.add(new NegateAndMatch(from("", "abcdcba"), from("a", "abc", "cbaabc", "aabbcc")))
        group.add(new NegateAndMatch(from("abcdcba"), from("", "a", "abc", "cbaabc", "aabbcc")))
        group.add(new NegateAndMatch(from("aaaaaaaaab"), from("", "a", "aa", "aaaaaaaaa")))
        group.add(new NegateAndMatch(from("a", "aa"), from("aaa")))
        group.add(new NegateAndMatch(from("a", "ab", "abca"), from("abc", "cba")))
        tasks.add(group)
    }

    private def addCaptureGroupExercises(descriptions : Descriptions) {
        val group = new TaskGroup("Grupos de Captura", "match.capture", descriptions)
        group.add(new CaptureGroup("abcdef", "abcdef"))
        group.add(new CaptureGroup("abcdef1a", "abcdef"))
        group.add(new CaptureGroup("abcdef1a", "abcdef", "1a"))
        group.add(new CaptureGroup("abcdef1a", "abcdef1a", "abcdef", "1"))
        group.add(new CaptureGroup("aa", "a"))
        group.add(new CaptureGroup("aaaa", "aa"))
        group.add(new CaptureGroup("aaaaaaaa", "aaaa"))
        tasks.add(group)
    }

    private def addAnchoringExercises(descriptions : Descriptions) {
        val group = new TaskGroup("Ancoras", "match.anchor", descriptions)
        group.add(new NegateAndFind(from("/blog", "/blog/1abc"), from("/blog/1", "/blog/2")))
        group.add(new NegateAndFind(from("/blog/2"), from("/blog")))
        group.add(new NegateAndFind(from("a/blog/"), from("/blog/")))
        group.add(new NegateAndFind(from("blogueiro"), from("blog")))
        tasks.add(group)
    }

    private def addModesExercises(descriptions : Descriptions) {
        val group = new TaskGroup("Modos da RegEx", "match.modes", descriptions)
        group.add(new MassNegateAndMatch("modes/regex", "Qual RegEx &eacute; capaz de reconhecer todas " +
            "as variações de escrita de RegEx (regex, Regex, ...)?"))
        group.add(new Match(from("\nabcd", "a\nbcd", "ab\ncd", "abc\nd", "abcd\n")))
        tasks.add(group)
    }

    private def addRealWorldRegexes(descriptions : Descriptions) {
        val group = new TaskGroup("RegEx no Mundo Real", "match.real.world", descriptions)
        group.add(new MassNegateAndMatch("real/dates", "Qual RegEx &eacute; capaz de reconhecer datas como 1/1/1970?"))
        group.add(new MassNegateAndMatch("real/ipV4V6", "Qual RegEx &eacute; capaz de reconhecer datas como 1/1/1970?"))
        group.add(new MassNegateAndMatch("real/blog.urls", "Qual RegEx &eacute; capaz de reconhecer datas como 1/1/1970?"))
        group.add(new MassNegateAndMatch("real/email", "Qual RegEx &eacute; capaz de reconhecer datas como 1/1/1970?"))
        group.add(new MassNegateAndMatch("real/cpf", "Qual RegEx &eacute; capaz de reconhecer datas como 1/1/1970?"))
        group.add(new MassNegateAndMatch("real/log", "Qual RegEx &eacute; capaz de reconhecer datas como 1/1/1970?"))
        tasks.add(group)
    }

    def task(index : Int) = tasks.at(index)

    def getSize = tasks.size

    def getDescription = "Um jogo muito legal para aprender RegEx"

    def getTasks = tasks.all

    def getName = "RegEx"

    def hasNextTask(index : Int) = nextTask(index) < getSize

    def nextTask(index : Int) = index + 1
}