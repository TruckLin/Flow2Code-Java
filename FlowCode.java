 i m p o r t   j a v a . u t i l . S c a n n e r ; 
 p u b l i c   c l a s s   F l o w C o d e { 
         p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g [ ]   a r g s ) { 
 i n t   x ; 
                 
                 / / T e s t i n g 
                 S y s t e m . o u t . p r i n t l n ( " I n   s u b P r o c e s s   :   " ) ; 
                 
                 S c a n n e r   i n   =   n e w   S c a n n e r ( S y s t e m . i n ) ; 
                 x   =   i n . n e x t I n t ( ) ; 
                 i n . c l o s e ( ) ; 
                 t r y   { 
                         S y s t e m . o u t . p r i n t l n ( " W a i t i n g " ) ; 
                         T h r e a d . s l e e p ( 5 0 0 ) ; 
                 }   c a t c h   ( I n t e r r u p t e d E x c e p t i o n   e )   { 
                         / /   T O D O   A u t o - g e n e r a t e d   c a t c h   b l o c k 
                         e . p r i n t S t a c k T r a c e ( ) ; 
                 } 
                 
                 S y s t e m . o u t . p r i n t l n (   " N u m b e r   e n t e r e d   i s   :   "   +   x   ) ; 
         } 
 }