 i m p o r t   j a v a . u t i l . S c a n n e r ;   
 p u b l i c   c l a s s   F l o w C o d e { 
         p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g [ ]   a r g s ) { 
                 S c a n n e r   s c   =   n e w   S c a n n e r ( S y s t e m . i n ) ; 
                 i n t   a ; 
                 i n t   b ; 
                 a   =   5 7 ; 
                 b   =   0 ; 
                 S y s t e m . o u t . p r i n t l n (   " a   i s   a n   i n t e a g e r   b e t w e e n   0 ~ 1 0 0 , "   ) ; 
                 S y s t e m . o u t . p r i n t l n (   " I n p u t   a n   i n t e g e r   b   t o   g u e s s   t h e   v a l u e   o f   a . . . "   ) ; 
                 w h i l e (   a   ! =   b   )   { 
                         b   =   s c . n e x t I n t ( ) ; 
                         i f (   b   <   a   )   { 
                                 i f (   a   ! =   b   )   { 
                                         S y s t e m . o u t . p r i n t l n (   " a   i s   >   " + b   ) ; 
                                 } 
                         }   e l s e   { 
                                 S y s t e m . o u t . p r i n t l n (   " a   i s   <   " + b   ) ; 
                         } 
                 } 
                 S y s t e m . o u t . p r i n t l n (   " Y o u   g e t   i t   ! !   a   i s   " +   a   ) ; 
                 s c . c l o s e ( ) ; 
         } 
 }