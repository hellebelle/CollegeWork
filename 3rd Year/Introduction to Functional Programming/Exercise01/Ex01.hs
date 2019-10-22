-- Name: Anna Maliakal,  Username: maliakaa
module Ex01 where
import Data.Char (toUpper) -- needed for Part 1

{- Part 1

Write a function 'raise' that converts a string to uppercase

Function 'toUpper :: Char -> Char' converts a character to uppercase
if it is lowercase. All other characters are unchanged

-}
raise :: String -> String
raise [] = []
raise x = map toUpper x


{- Part 2

Write a function 'nth' that returns the nth element of a list

-}
nth :: Int -> [a] -> a
nth n xs = xs !! (n - 1)


{- Part 3

write a function commonLen that compares two sequences
and reports the length of the prefix they have in common.

-}
commonLen :: Eq a => [a] -> [a] -> Int
commonLen [] [] = 0
commonLen _ [] = 0
commonLen [] _ = 0
commonLen (x:xs) (y:ys)
        |x == y = 1 +  commonLen xs ys
        |otherwise = 0

